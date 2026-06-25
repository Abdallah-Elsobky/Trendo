package iti.gov.trendo.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import iti.gov.trendo.presentation.components.*
import iti.gov.trendo.presentation.navigation.LocalNavController
import iti.gov.trendo.presentation.navigation.Screen

private fun regionDisplayName(code: String): String {
    return when (code.lowercase()) {
        "us" -> "United States"
        "eg" -> "Egypt"
        "gb" -> "United Kingdom"
        "uk" -> "United Kingdom"
        "fr" -> "France"
        "de" -> "Germany"
        "jp" -> "Japan"
        "cn" -> "China"
        "sa" -> "Saudi Arabia"
        "ae" -> "UAE"
        "au" -> "Australia"
        "ca" -> "Canada"
        "in" -> "India"
        "br" -> "Brazil"
        "ru" -> "Russia"
        "it" -> "Italy"
        "es" -> "Spain"
        "mx" -> "Mexico"
        "kr" -> "South Korea"
        "ng" -> "Nigeria"
        "za" -> "South Africa"
        "ar" -> "Argentina"
        "tr" -> "Turkey"
        "pk" -> "Pakistan"
        "id" -> "Indonesia"
        "th" -> "Thailand"
        "ph" -> "Philippines"
        else -> code.uppercase()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    var isRegionSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Trendo",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    val selectedCode = state.selectedRegion?.lowercase()
                    val regionLabel = if (state.selectedRegion != null)
                        regionDisplayName(state.selectedRegion!!)
                    else "Global"

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (state.selectedRegion != null)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { isRegionSheetVisible = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (state.selectedRegion != null)
                                        state.selectedRegion!!.uppercase().take(2)
                                    else "🌐",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = regionLabel,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (state.selectedRegion != null)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )

            SearchBar(
                query = state.searchQuery,
                onQueryChanged = { viewModel.onEvent(HomeUiEvent.OnSearchQueryChanged(it)) },
                placeholder = "Search trending stories...",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    CategoryChip(
                        label = "All",
                        isSelected = state.selectedCategory == null,
                        onClick = { viewModel.onEvent(HomeUiEvent.OnCategorySelected(null)) }
                    )
                }
                items(state.categories) { category ->
                    CategoryChip(
                        label = category.replaceFirstChar { it.uppercase() },
                        isSelected = state.selectedCategory == category,
                        onClick = { viewModel.onEvent(HomeUiEvent.OnCategorySelected(category)) }
                    )
                }
            }

            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { viewModel.onEvent(HomeUiEvent.OnRefresh) },
                modifier = Modifier.weight(1f)
            ) {
                when {
                    state.errorMessage != null && state.articles.isEmpty() -> {
                        ErrorState(
                            message = state.errorMessage ?: "Failed to load stories.",
                            onRetry = {
                                viewModel.onEvent(HomeUiEvent.OnClearError)
                                viewModel.onEvent(HomeUiEvent.OnRefresh)
                            }
                        )
                    }
                    state.articles.isEmpty() && !state.isLoading -> {
                        EmptyState(
                            title = "No Articles Found",
                            message = "Try a different category or refresh the feed."
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            items(state.articles, key = { it.id }) { article ->
                                ArticleCard(
                                    article = article,
                                    onArticleClick = { id ->
                                        navController.navigateTo(Screen.Details(id))
                                    },
                                    onFavoriteToggle = { id, isFav ->
                                        viewModel.onEvent(HomeUiEvent.OnFavoriteToggled(id, isFav))
                                    }
                                )
                            }
                            item { Spacer(modifier = Modifier.height(80.dp)) }
                        }
                    }
                }
            }
        }

        CustomBottomSheet(
            visible = isRegionSheetVisible,
            onDismiss = { isRegionSheetVisible = false },
            title = "Select Region",
            modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    RegionRow(
                        code = null,
                        displayName = "Global Feed",
                        isSelected = state.selectedRegion == null,
                        onClick = {
                            viewModel.onEvent(HomeUiEvent.OnRegionSelected(null))
                            isRegionSheetVisible = false
                        }
                    )
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }

                if (state.regions.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No regions available yet.\nPull to refresh the feed.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(state.regions) { region ->
                        RegionRow(
                            code = region,
                            displayName = regionDisplayName(region),
                            isSelected = state.selectedRegion?.lowercase() == region.lowercase(),
                            onClick = {
                                viewModel.onEvent(HomeUiEvent.OnRegionSelected(region))
                                isRegionSheetVisible = false
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.surface

    val textColor = if (isSelected) Color.White
    else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = bgColor,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun RegionRow(
    code: String?,
    displayName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val badgeText = code?.uppercase()?.take(2) ?: "GL"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            )
            .padding(horizontal = 8.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badgeText,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                fontWeight = FontWeight.ExtraBold,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = displayName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
