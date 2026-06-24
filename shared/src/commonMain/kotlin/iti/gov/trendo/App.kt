package iti.gov.trendo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import iti.gov.trendo.data.local.dao.NewsDao
import iti.gov.trendo.data.local.db.AppDatabase
import iti.gov.trendo.data.mapper.toEntity
import iti.gov.trendo.data.remote.client.HttpClientFactory
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSource
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSourceImpl
import iti.gov.trendo.data.remote.dto.NewsItem
import iti.gov.trendo.data.remote.service.NewsApiServiceImpl
import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.onError
import iti.gov.trendo.data.remote.utils.onSuccess
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import co.touchlab.kermit.Logger
import iti.gov.trendo.data.local.datasource.NewsLocalDataSource
import iti.gov.trendo.data.mapper.toEntityList

@Composable
fun App() {
    val api = getKoin().get<NewsRemoteDataSource>()
    val db = getKoin().get<NewsDao>()
    val local = getKoin().get<NewsLocalDataSource>()
    var isLoading by remember { mutableStateOf(false) }
    var newsList: List<NewsItem?> by remember { mutableStateOf(emptyList()) }
    var errorMessage by remember { mutableStateOf<NetworkError?>(null) }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().height(200.dp)
                    .padding(22.dp),
                value = newsList.toString(),
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(Modifier.height(33.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = null
                        val result = api.getArticles()
                        result.onSuccess { response ->
                            newsList = response.news ?: emptyList()
//                            Logger.d { newsList.toEntityList().toString() }
//                            db.insertNews(newsList.toEntityList())
                        }
                        result.onError { error ->
                            errorMessage = error
                        }
                        isLoading = false
                        local.getNews().collect {
                            Logger.d { "LOCO" + it.toString() }
                        }
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(15.dp),
                        strokeWidth = 1.dp,
                        color = Color.White
                    )
                } else {
                    Text(text = "Search")
                }
            }
            errorMessage?.let {
                Text(
                    text = "Error: $it",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
