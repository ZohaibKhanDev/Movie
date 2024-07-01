package com.example.movie.bottomnavigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movie.animation.ScaleTransitionDirection
import com.example.movie.animation.scaleIntoContainer
import com.example.movie.animation.scaleOutOfContainer
import com.example.movie.screens.Detail
import com.example.movie.screens.FavScreen
import com.example.movie.screens.HomeScreen
import com.example.movie.screens.PopularMovies
import com.example.movie.screens.Tv_Series

@Composable
fun Navigaton(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route, enterTransition = {
            scaleIntoContainer()
        },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            HomeScreen(navController = navController)
        }
        composable(Screen.PopularMovies.route, enterTransition = {
            scaleIntoContainer()
        },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            PopularMovies(navController = navController)
        }

        composable(Screen.Tv_Series.route, enterTransition = {
            scaleIntoContainer()
        },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            Tv_Series(navController = navController)
        }

        composable(Screen.Fav.route, enterTransition = {
            scaleIntoContainer()
        },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            FavScreen(navController = navController)
        }

        composable(
            Screen.DetailScreen.route + "/{id}/{title}/{des}/{pic}/{video}/{date}/{star}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                },
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("des") {
                    type = NavType.StringType
                },
                navArgument("pic") {
                    type = NavType.StringType
                },
                navArgument("video") {
                    type = NavType.StringType
                },
                navArgument("date") {
                    type = NavType.StringType
                },

                navArgument("star") {
                    type = NavType.StringType
                },
            ), enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ) {
            val id = it.arguments?.getInt("id")
            val title = it.arguments?.getString("title")
            val des = it.arguments?.getString("des")
            val pic = it.arguments?.getString("pic")
            val video = it.arguments?.getString("video")
            val date = it.arguments?.getString("date")
            val star = it.arguments?.getString("star")
            Detail(navController = navController, id, title, des, pic, video, date,star)
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavEntry() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
        Navigaton(navController)
    }
}


sealed class Screen(
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {
    object HomeScreen :
        Screen("Home", selectedIcon = Icons.Filled.Home, unSelectedIcon = Icons.Outlined.Home)

    object PopularMovies : Screen(
        "Popular",
        selectedIcon = Icons.Filled.Movie,
        unSelectedIcon = Icons.Outlined.Movie
    )

    object Tv_Series : Screen(
        "Tv_Series",
        selectedIcon = Icons.Filled.LiveTv,
        unSelectedIcon = Icons.Outlined.LiveTv
    )

    object Fav : Screen(
        "Fav",
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.FavoriteBorder
    )

    object DetailScreen : Screen(
        "DetailScreen",
        selectedIcon = Icons.Filled.Search,
        unSelectedIcon = Icons.Outlined.Search
    )
}


@Composable
fun BottomNavigation(navController: NavController) {
    val item = listOf(
        Screen.HomeScreen,
        Screen.PopularMovies,
        Screen.Tv_Series,
        Screen.Fav

    )

    NavigationBar(containerColor = Color(0XFF383737), contentColor = Color.White) {
        val navStack by navController.currentBackStackEntryAsState()
        val current = navStack?.destination?.route
        item.forEach {
            NavigationBarItem(selected = current == it.route, onClick = {
                navController.navigate(it.route) {
                    navController.graph?.let {
                        it.route?.let { it1 -> popUpTo(it1) }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }, icon = {
                if (current == it.route) {
                    Icon(
                        imageVector = it.selectedIcon,
                        contentDescription = "",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        imageVector = it.selectedIcon,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

            },
                label = {
                    AnimatedVisibility(visible = current == it.route) {
                        Text(text = it.route, color = Color.Red)
                    }

                }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0XFF383737))
            )
        }
    }
}