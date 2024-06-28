package com.example.movie.restapi

import com.example.movie.allmovies.All_Movies

interface ApiClient {
    suspend fun getAllMovie(): Movies

    suspend fun AllMovie():All_Movies
}