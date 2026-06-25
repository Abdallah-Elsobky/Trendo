package iti.gov.trendo.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Facebook-inspired Blue palette
val TrendoBlue = Color(0xFF1877F2)        // Facebook primary blue
val TrendoBlueDark = Color(0xFF0D65D9)    // Darker blue for pressed states
val TrendoBlueLight = Color(0xFFE7F0FD)   // Pale blue for containers
val TrendoBlueOnLight = Color(0xFF0D47A1) // Dark text on blue containers

private val LightColorScheme = lightColorScheme(
    primary = TrendoBlue,
    onPrimary = Color.White,
    primaryContainer = TrendoBlueLight,
    onPrimaryContainer = TrendoBlueOnLight,
    secondary = Color(0xFF42526E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8ECF4),
    onSecondaryContainer = Color(0xFF172B4D),
    background = Color(0xFFF0F2F5),        // Facebook feed grey background
    onBackground = Color(0xFF1C1E21),
    surface = Color(0xFFFFFFFF),           // Card white
    surfaceVariant = Color(0xFFEBEDF0),    // Subtle grey for chips/bars
    onSurface = Color(0xFF1C1E21),
    onSurfaceVariant = Color(0xFF65676B),  // Facebook grey text
    outline = Color(0xFFCDD1D5),
    outlineVariant = Color(0xFFE4E6EB),
    error = Color(0xFFE02020),
    onError = Color.White,
    errorContainer = Color(0xFFFFE5E5),
    onErrorContainer = Color(0xFF7F0000)
)

@Composable
fun TrendoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
