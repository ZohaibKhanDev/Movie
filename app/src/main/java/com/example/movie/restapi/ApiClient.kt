package com.example.movie.restapi

import com.example.movie.allmovies.All_Movies
import com.example.movie.people.People
import com.example.movie.similer.Similar
import com.example.movie.tv.Tv
import com.example.movie.tvseries.popular.All_Popular
import com.example.movie.tvseries.toprated.Top_Rated

interface ApiClient {
    suspend fun getAllMovie(): PopularMovies

    suspend fun AllMovie():All_Movies

    suspend fun AllPopularSeries(): All_Popular

    suspend fun AllTopRatedSeries():Top_Rated

    suspend fun Tv():Tv

    suspend fun People(): People

    suspend fun SimilarMovies():Similar
}