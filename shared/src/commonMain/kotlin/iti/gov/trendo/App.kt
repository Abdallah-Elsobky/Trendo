package iti.gov.trendo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import iti.gov.trendo.presentation.navigation.LocalNavController
import iti.gov.trendo.presentation.navigation.NavigationController
import iti.gov.trendo.presentation.navigation.Screen
import iti.gov.trendo.presentation.theme.TrendoTheme
import iti.gov.trendo.presentation.ui.details.DetailsDialog
import iti.gov.trendo.presentation.ui.favorite.FavoriteScreen
import iti.gov.trendo.presentation.ui.home.HomeScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val navController = remember { NavigationController() }

    CompositionLocalProvider(LocalNavController provides navController) {
        TrendoTheme {
            val currentScreen = navController.currentScreen
            val baseScreen = navController.baseScreen

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                when (baseScreen) {
                    is Screen.Home -> HomeScreen(viewModel = koinViewModel())
                    is Screen.Favorites -> FavoriteScreen(viewModel = koinViewModel())
                    else -> HomeScreen(viewModel = koinViewModel())
                }

                (currentScreen as? Screen.Details)?.let { details ->
                    DetailsDialog(
                        articleId = details.articleId,
                        viewModel = koinViewModel(),
                        onDismiss = { navController.navigateBack() }
                    )
                }

                Surface(
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 12.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                        .height(60.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val isHomeSelected = baseScreen is Screen.Home
                        NavBarItem(
                            icon = Icons.Default.Home,
                            label = "Home",
                            isSelected = isHomeSelected,
                            onClick = { navController.navigateTo(Screen.Home) }
                        )

                        val isFavSelected = baseScreen is Screen.Favorites
                        NavBarItem(
                            icon = Icons.Default.Favorite,
                            label = "Saved",
                            isSelected = isFavSelected,
                            onClick = { navController.navigateTo(Screen.Favorites) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavBarItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(52.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(if (isSelected) 26.dp else 22.dp)
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .size(width = 18.dp, height = 3.dp)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(2.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {}
                }
            }
        }
    }
}
