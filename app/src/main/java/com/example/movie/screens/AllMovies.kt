package com.example.movie.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movie.allmovies.All_Movies
import com.example.movie.allmovies.Result
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.ResultState
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllMovies(navController: NavController) {
    val viewModel: MainViewModel = koinInject()
    var allMovieData by remember {
        mutableStateOf<All_Movies?>(null)
    }
    var search by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.AllMovies()
    }

    val state by viewModel.allMovies.collectAsState()

    when (state) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state as ResultState.Error).error
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true

        }

        is ResultState.Success -> {
            isLoading = false
            val success = (state as ResultState.Success).success
            allMovieData = success
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier
                            .width(330.dp)
                            .background(Color.Black)
                            .padding(end = 7.dp)
                            .height(53.dp),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(
                            fontSize = 14.sp

                        ),
                        placeholder = { Text(text = "Search Movie") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search, contentDescription = ""
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0XFF14171c),
                            unfocusedContainerColor = Color(0XFF14171c),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White.copy(alpha = 0.70f),
                            unfocusedTextColor = Color.White.copy(alpha = 0.70f)
                        ),
                    )

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .clip(RoundedCornerShape(11.dp))
                            .height(50.dp)
                            .background(Color(0XFF14171c)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = "",
                            tint = Color.DarkGray
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }) { it ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(top = it.calculateTopPadding(), bottom = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                allMovieData?.results?.let {
                    items(it) { movies ->
                        AllMovieItem(result = movies)
                    }
                }
            }
        }

    }
}

@Composable
fun AllMovieItem(result: Result) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(190.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            Color.DarkGray.copy(alpha = 0.40f)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${result.posterPath}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 26.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = result.originalTitle,
                modifier = Modifier.align(Alignment.BottomCenter),
                color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
            )
        }


    }
}