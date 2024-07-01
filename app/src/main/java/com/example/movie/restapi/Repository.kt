package com.example.movie.restapi

import com.example.movie.allmovies.All_Movies
import com.example.movie.people.People
import com.example.movie.similer.Similar
import com.example.movie.tv.Tv
import com.example.movie.tvseries.popular.All_Popular
import com.example.movie.tvseries.toprated.Top_Rated

class Repository: ApiClient {
    override suspend fun getAllMovie(): PopularMovies {
        return MovieApiClient.Movie()
    }

    override suspend fun AllMovie(): All_Movies {
        return MovieApiClient.AllMovies()
    }

    override suspend fun AllPopularSeries(): All_Popular {
        return MovieApiClient.AllPopularSeries()
    }

    override suspend fun AllTopRatedSeries(): Top_Rated {
        return MovieApiClient.AllTopRatedSeries()
    }

    override suspend fun Tv(): Tv {
        return MovieApiClient.Tv()
    }

    override suspend fun People(): People {
        return MovieApiClient.People()
    }

    override suspend fun SimilarMovies(): Similar {
        return MovieApiClient.SimilarMovies()
    }
}