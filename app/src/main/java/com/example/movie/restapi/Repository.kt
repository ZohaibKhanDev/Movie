package com.example.movie.restapi

import com.example.movie.allmovies.All_Movies

class Repository: ApiClient {
    override suspend fun getAllMovie(): Movies {
        return MovieApiClient.Movie()
    }

    override suspend fun AllMovie(): All_Movies {
        return MovieApiClient.AllMovies()
    }
}