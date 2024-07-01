package com.example.movie.tvseries.toprated


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Top_Rated(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<TopRatedResult>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)