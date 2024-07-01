package com.example.movie.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movie.bottomnavigation.Screen
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.ResultState
import com.example.movie.tvseries.popular.PopularResult
import com.example.movie.tvseries.toprated.TopRatedResult
import com.example.movie.tvseries.toprated.Top_Rated
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Tv_Series(navController: NavController) {
    val viewModel: MainViewModel = koinInject()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getALlTopRatedSeries()
    }

    var topRatedData by remember { mutableStateOf<Top_Rated?>(null) }

    val topRatedState by viewModel.allTopRatedSeries.collectAsState()

    when (topRatedState) {
        is ResultState.Error -> {
            isLoading = false
            val error = (topRatedState as ResultState.Error).error
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            isLoading = false
            val success = (topRatedState as ResultState.Success).success
            topRatedData = success
        }
    }


    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Top Rated") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0XFFb3d088), titleContentColor = Color.White
            )
        )
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFF1b1c18))
                .padding(top = it.calculateTopPadding(), bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            topRatedData?.results?.let {
                items(it) {
                    TopRatedItem(topRatedResult = it, navController)
                }
            }
        }
    }
}


@Composable
fun TopRatedItem(topRatedResult: TopRatedResult, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                val encodedTitle = Uri.encode(topRatedResult.originalName)
                val date = Uri.encode(topRatedResult.firstAirDate)
                val encodedDescription = Uri.encode(topRatedResult.overview)
                val stars = Uri.encode(topRatedResult.popularity.toString())
                val encodedPic =
                    Uri.encode("https://image.tmdb.org/t/p/w342${topRatedResult.posterPath}")
                val encodedVideo =
                    Uri.encode("https://image.tmdb.org/t/p/w342${topRatedResult.backdropPath}")
                navController.navigate(Screen.DetailScreen.route + "/${topRatedResult.id}/$encodedTitle/$encodedDescription/$encodedPic/$encodedVideo/$date/$stars")
            }
            .fillMaxWidth()
            .padding(14.dp)
            .height(750.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            Color(0XFF44483d)
        ), shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = topRatedResult.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 6.dp),
                color = Color.White, maxLines = 1,
            )
            Spacer(modifier = Modifier.height(20.dp))

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${topRatedResult.posterPath}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(360.dp)
            )


            Text(
                text = "Overview",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(15.dp),
            )

            Text(
                text = topRatedResult.overview,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp), maxLines = 4
            )


            Text(
                text = "Release Date",
                fontWeight = FontWeight.SemiBold,
                color = Color.White, fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp)
            )


            Text(
                text = topRatedResult.firstAirDate,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp)
            )


            Text(
                text = "Average Vote",
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp), fontSize = 20.sp
            )


            Text(
                text = topRatedResult.voteAverage.toString(),
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
    }
}
