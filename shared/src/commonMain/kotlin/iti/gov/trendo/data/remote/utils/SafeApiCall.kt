package iti.gov.trendo.data.remote.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeApiCall(
    httpCall: () -> HttpResponse
): Result<T, NetworkError> {
    return try {

        val response = httpCall()

        when (response.status.value) {

            in 200..299 -> {
                Result.Success(response.body<T>())
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)

            in 400..499 -> Result.Error(NetworkError.CONFLICT)

            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)

            else -> Result.Error(NetworkError.UNKNOWN)
        }

    } catch (e: UnresolvedAddressException) {
        Result.Error(NetworkError.NO_INTERNET)

    } catch (e: SerializationException) {
        Result.Error(NetworkError.SERIALIZATION)

    } catch (e: Exception) {
        Result.Error(NetworkError.UNKNOWN)
    }
}