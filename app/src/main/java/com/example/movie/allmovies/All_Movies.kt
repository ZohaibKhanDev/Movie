package com.example.movie.allmovies


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class All_Movies(
    @SerialName("page")
    val page: Int?=null,
    @SerialName("results")
    val results: List<Result>?=null,
    @SerialName("total_pages")
    val totalPages: Int?=null,
    @SerialName("total_results")
    val totalResults: Int?=null
)