package iti.gov.trendo.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.gov.trendo.data.remote.utils.onError
import iti.gov.trendo.data.remote.utils.onSuccess
import iti.gov.trendo.domain.usecase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class HomeViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
    private val refreshSearchUseCase: RefreshSearchUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val refreshRegionsUseCase: RefreshRegionsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _selectedCategory = MutableStateFlow<String?>(null)
    private val _selectedRegion = MutableStateFlow<String?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _errorMessage = MutableStateFlow<String?>(null)

    private val debouncedSearchQuery = _searchQuery
        .debounce(500)
        .distinctUntilChanged()
    private val articlesFlow = combine(
        _searchQuery,
        _selectedCategory,
        _selectedRegion
    ) { query, category, region ->
        Triple(query, category, region)
    }.flatMapLatest { (query, category, region) ->
        if (query.isBlank()) {
            getNewsUseCase(category = category, region = region)
        } else {
            searchNewsUseCase(query = query, category = category, region = region)
        }
    }

    private data class HomeFiltersState(
        val isLoading: Boolean = false,
        val selectedCategory: String? = null,
        val selectedRegion: String? = null,
        val searchQuery: String = "",
        val errorMessage: String? = null
    )

    private val filtersStateFlow = combine(
        _isLoading,
        _selectedCategory,
        _selectedRegion,
        _searchQuery,
        _errorMessage
    ) { isLoading, category, region, query, error ->
        HomeFiltersState(isLoading, category, region, query, error)
    }

    val state: StateFlow<HomeUiState> = combine(
        filtersStateFlow,
        articlesFlow,
        getCategoriesUseCase(),
        getRegionsUseCase()
    ) { filters, articles, categories, regions ->
        HomeUiState(
            isLoading = filters.isLoading,
            articles = articles,
            categories = categories,
            regions = regions,
            selectedCategory = filters.selectedCategory,
            selectedRegion = filters.selectedRegion,
            searchQuery = filters.searchQuery,
            errorMessage = filters.errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isLoading = true)
    )

    init {
        viewModelScope.launch {
            refreshCategoriesUseCase()
            refreshRegionsUseCase()
        }
        
        refreshFeed()

        viewModelScope.launch {
            debouncedSearchQuery.collect { query ->
                if (query.isNotBlank()) {
                    triggerSearchRefresh(query)
                }
            }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchQueryChanged -> {
                _searchQuery.value = event.query
            }
            is HomeUiEvent.OnCategorySelected -> {
                _selectedCategory.value = event.category
                refreshFeed()
            }
            is HomeUiEvent.OnRegionSelected -> {
                _selectedRegion.value = event.region
                refreshFeed()
            }
            is HomeUiEvent.OnFavoriteToggled -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.articleId, event.isFavorite)
                }
            }
            HomeUiEvent.OnRefresh -> {
                refreshFeed()
            }
            HomeUiEvent.OnClearError -> {
                _errorMessage.value = null
            }
        }
    }

    private fun refreshFeed() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = refreshNewsUseCase(
                category = _selectedCategory.value,
                region = _selectedRegion.value
            )
            result.onSuccess {
                _isLoading.value = false
            }
            result.onError { error ->
                _isLoading.value = false
                _errorMessage.value = error.name
            }
        }
    }

    private suspend fun triggerSearchRefresh(query: String) {
        _isLoading.value = true
        val result = refreshSearchUseCase(
            query = query,
            category = _selectedCategory.value
        )
        result.onSuccess {
            _isLoading.value = false
        }
        result.onError { error ->
            _isLoading.value = false
            _errorMessage.value = error.name
        }
    }
}
