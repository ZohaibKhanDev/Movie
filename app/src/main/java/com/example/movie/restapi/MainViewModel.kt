package com.example.movie.restapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allMovie = MutableStateFlow<ResultState<Movies>>(ResultState.Loading)
    val allMovie: StateFlow<ResultState<Movies>> = _allMovie.asStateFlow()


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