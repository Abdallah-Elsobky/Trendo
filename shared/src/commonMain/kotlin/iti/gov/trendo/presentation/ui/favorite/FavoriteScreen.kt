package iti.gov.trendo.presentation.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import iti.gov.trendo.presentation.components.ArticleCard
import iti.gov.trendo.presentation.components.EmptyState
import iti.gov.trendo.presentation.navigation.LocalNavController
import iti.gov.trendo.presentation.navigation.Screen
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Saved Articles",
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                state.favorites.isEmpty() -> {
                    EmptyState(
                        title = "No Saved Stories",
                        message = "Tap the heart icon on articles to save them here for offline reading."
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.favorites, key = { it.id }) { article ->
                            ArticleCard(
                                article = article,
                                onArticleClick = { id ->
                                    navController.navigateTo(Screen.Details(id))
                                },
                                onFavoriteToggle = { id, isFav ->
                                    viewModel.onEvent(FavoriteUiEvent.OnFavoriteToggled(id, isFav))
                                }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(72.dp))
                        }
                    }
                }
            }
        }
    }
}
