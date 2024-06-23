package com.example.movie.restapi

class Repository: ApiClient {
    override suspend fun getAllMovie(): Movies {
        return MovieApiClient.Movie()
    }
}