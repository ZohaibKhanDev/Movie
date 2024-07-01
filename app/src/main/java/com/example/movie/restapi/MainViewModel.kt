package com.example.movie.restapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.allmovies.All_Movies
import com.example.movie.people.People
import com.example.movie.similer.Similar
import com.example.movie.tv.Tv
import com.example.movie.tvseries.popular.All_Popular
import com.example.movie.tvseries.toprated.Top_Rated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allMovie = MutableStateFlow<ResultState<PopularMovies>>(ResultState.Loading)
    val allMovie: StateFlow<ResultState<PopularMovies>> = _allMovie.asStateFlow()


    private val _allMovies = MutableStateFlow<ResultState<All_Movies>>(ResultState.Loading)
    val allMovies: StateFlow<ResultState<All_Movies>> = _allMovies.asStateFlow()


    private val _allPopulerSeries = MutableStateFlow<ResultState<All_Popular>>(ResultState.Loading)
    val allPopularSeries: StateFlow<ResultState<All_Popular>> = _allPopulerSeries.asStateFlow()


    private val _allTopRatedSeries = MutableStateFlow<ResultState<Top_Rated>>(ResultState.Loading)
    val allTopRatedSeries: StateFlow<ResultState<Top_Rated>> = _allTopRatedSeries.asStateFlow()

    private val _allTv = MutableStateFlow<ResultState<Tv>>(ResultState.Loading)
    val allTv:StateFlow<ResultState<Tv>> = _allTv.asStateFlow()

    private val _allPeople = MutableStateFlow<ResultState<People>>(ResultState.Loading)
    val allPeople :StateFlow<ResultState<People>> = _allPeople.asStateFlow()


    private val _allSimiler = MutableStateFlow<ResultState<Similar>>(ResultState.Loading)
    val allSimilar:StateFlow<ResultState<Similar>> = _allSimiler.asStateFlow()


    fun similarMovies(){
        viewModelScope.launch {
            _allSimiler.value=ResultState.Loading
            try {
                val response=repository.SimilarMovies()
                _allSimiler.value=ResultState.Success(response)
            }catch (e:Exception){
                _allSimiler.value=ResultState.Error(e)
            }
        }
    }


    fun getPeople(){
        viewModelScope.launch {
            _allPeople.value=ResultState.Loading
            try {
                val response=repository.People()
                _allPeople.value=ResultState.Success(response)
            }catch (e:Exception){
                _allPeople.value=ResultState.Error(e)
            }
        }
    }



    fun getAllTv(){
        viewModelScope.launch {
            _allTv.value=ResultState.Loading
            try {
                val response=repository.Tv()
                _allTv.value=ResultState.Success(response)
            }catch (e:Exception){
                _allTv.value=ResultState.Error(e)
            }
        }
    }

    fun getALlTopRatedSeries() {
        viewModelScope.launch {
            _allTopRatedSeries.value = ResultState.Loading
            try {
                val response = repository.AllTopRatedSeries()
                _allTopRatedSeries.value = ResultState.Success(response)
            } catch (e: Exception) {
                _allTopRatedSeries.value = ResultState.Error(e)
            }
        }
    }

    fun getAllPopularSeries() {
        viewModelScope.launch {
            _allPopulerSeries.value = ResultState.Loading
            try {
                val response = repository.AllPopularSeries()
                _allPopulerSeries.value = ResultState.Success(response)
            } catch (e: Exception) {
                _allPopulerSeries.value = ResultState.Error(e)
            }
        }
    }


    fun AllMovies() {
        viewModelScope.launch {
            _allMovies.value = ResultState.Loading
            try {
                val response = repository.AllMovie()
                _allMovies.value = ResultState.Success(response)
            } catch (e: Exception) {
                _allMovies.value = ResultState.Error(e)
            }
        }
    }

    fun getAllMovies() {
        viewModelScope.launch {
            _allMovie.value = ResultState.Loading
            try {
                val response = repository.getAllMovie()
                _allMovie.value = ResultState.Success(response)
            } catch (e: Exception) {
                _allMovie.value = ResultState.Error(e)
            }
        }

    }
}