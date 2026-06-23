package iti.gov.trendo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("categories")
    val categories: List<String>,
    @SerialName("description")
    val description: String,
    @SerialName("status")
    val status: String
)