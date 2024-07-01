package com.example.movie.tvseries.popular


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class All_Popular(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<PopularResult>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)