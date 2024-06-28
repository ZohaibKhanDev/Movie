package com.example.movie.restapi

import com.example.movie.allmovies.All_Movies
import com.example.movie.restapi.Constant.API_KEY
import com.example.movie.restapi.Constant.BASE_URL
import com.example.movie.restapi.Constant.TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object MovieApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }

            }
        }

        install(HttpTimeout){
            socketTimeoutMillis=TIMEOUT
            connectTimeoutMillis=TIMEOUT
            requestTimeoutMillis=TIMEOUT
        }
    }

    suspend fun Movie(): Movies {
        return client.get(BASE_URL+"3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&api_key=$API_KEY").body()
    }

    suspend fun AllMovies():All_Movies{
        return client.get("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&without_genres=99,10755&vote_count.gte=200&api_key=$API_KEY").body()
    }
}