package iti.gov.trendo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionResponse(
    @SerialName("description")
    val description: String,
    @SerialName("regions")
    val regions: List<String>,
    @SerialName("status")
    val status: String
)