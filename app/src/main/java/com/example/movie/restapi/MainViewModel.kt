package com.example.movie.restapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.allmovies.All_Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allMovie = MutableStateFlow<ResultState<Movies>>(ResultState.Loading)
    val allMovie: StateFlow<ResultState<Movies>> = _allMovie.asStateFlow()


    private val _allMovies = MutableStateFlow<ResultState<All_Movies>>(ResultState.Loading)
    val allMovies:StateFlow<ResultState<All_Movies>> = _allMovies.asStateFlow()


    fun AllMovies() {
        viewModelScope.launch {
            _allMovies.value=ResultState.Loading
            try {
                val response = repository.AllMovie()
                _allMovies.value = ResultState.Success(response)
            }catch (e:Exception){
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