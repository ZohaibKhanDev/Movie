package com.example.movie.appModule

import androidx.room.Room
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single { Repository() }
    single { MainViewModel(get()) }
}