package iti.gov.trendo.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage

@Composable
fun KtorAsyncImage(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    loadingPlaceholder: @Composable () -> Unit = {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    },
    errorPlaceholder: @Composable () -> Unit = {
        Box(modifier = modifier)
    }
) {
    if (url.isNullOrEmpty()) {
        errorPlaceholder()
        return
    }

    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        loading = { loadingPlaceholder() },
        error = { errorPlaceholder() }
    )
}
