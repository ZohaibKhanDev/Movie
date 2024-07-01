package com.example.movie.tv


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tv(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)