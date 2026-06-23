package iti.gov.trendo.data.remote.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import iti.gov.trendo.AppConfig
import kotlinx.serialization.json.Json

object HttpClientFactory {
    const val BASE_URL = "https://api.currentsapi.services/v2/"
    fun createClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url(BASE_URL)
                url.parameters.append("apiKey", AppConfig.API_KEY)
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

}