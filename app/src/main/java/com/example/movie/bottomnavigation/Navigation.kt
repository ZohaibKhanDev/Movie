package com.example.movie.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun Navigaton() {
    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreen.toString()){
        composable<HomeScreen> {

        }
    }
}

@Serializable
object HomeScreen

@Serializable
object Catagery

@Serializable
object Download

@Serializable
object Profile