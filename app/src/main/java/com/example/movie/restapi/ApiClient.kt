package com.example.movie.restapi

interface ApiClient {
    suspend fun getAllMovie(): Movies
}