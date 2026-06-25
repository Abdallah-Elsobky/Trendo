package iti.gov.trendo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.mp.KoinPlatform.getKoin

private val imageCache = mutableMapOf<String, ImageBitmap>()

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

    var imageState by remember(url) {
        mutableStateOf<ImageState>(
            imageCache[url]?.let { ImageState.Success(it) } ?: ImageState.Loading
        )
    }

    LaunchedEffect(url) {
        if (imageState is ImageState.Success) return@LaunchedEffect
        try {
            val client = getKoin().get<HttpClient>()
            val bytes = withContext(Dispatchers.Default) {
                client.get(url).readBytes()
            }
            val bitmap = withContext(Dispatchers.Default) {
                bytes.decodeToImageBitmap()
            }
            imageCache[url] = bitmap
            imageState = ImageState.Success(bitmap)
        } catch (e: Exception) {
            imageState = ImageState.Error
        }
    }

    when (val state = imageState) {
        is ImageState.Loading -> loadingPlaceholder()
        is ImageState.Success -> {
            Image(
                bitmap = state.bitmap,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        is ImageState.Error -> errorPlaceholder()
    }
}

sealed interface ImageState {
    data object Loading : ImageState
    data class Success(val bitmap: ImageBitmap) : ImageState
    data object Error : ImageState
}
