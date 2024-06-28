package com.example.movie.allmovies


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class All_Movies(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)