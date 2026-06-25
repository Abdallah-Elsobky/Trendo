package iti.gov.trendo.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

sealed interface Screen {
    data object Home : Screen
    data object Favorites : Screen
    data class Details(val articleId: String) : Screen
}

class NavigationController(initialScreen: Screen = Screen.Home) {
    var currentScreen by mutableStateOf<Screen>(initialScreen)
        private set

    var baseScreen by mutableStateOf<Screen>(
        if (initialScreen is Screen.Home || initialScreen is Screen.Favorites) initialScreen else Screen.Home
    )
        private set

    private val backStack = mutableStateListOf<Screen>(initialScreen)

    fun navigateTo(screen: Screen) {
        if (screen is Screen.Home || screen is Screen.Favorites) {
            baseScreen = screen
        }
        if (screen is Screen.Home) {
            backStack.clear()
            backStack.add(Screen.Home)
            currentScreen = Screen.Home
        } else {
            // Avoid duplicate details navigation in backstack
            if (backStack.lastOrNull() == screen) return
            backStack.add(screen)
            currentScreen = screen
        }
    }

    fun navigateBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            currentScreen = backStack.last()
            return true
        }
        return false
    }

    fun canNavigateBack(): Boolean {
        return backStack.size > 1
    }
}

val LocalNavController = staticCompositionLocalOf<NavigationController> {
    error("No NavigationController provided")
}
