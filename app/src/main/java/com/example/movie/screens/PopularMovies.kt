package com.example.movie.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movie.bottomnavigation.Screen
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.ResultState
import com.example.movie.tvseries.popular.All_Popular
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PopularMovies(navController: NavController) {
    val viewModel: MainViewModel = koinInject()
    var isLoading by remember {
        mutableStateOf(false)
    }
    var popularData by remember {
        mutableStateOf<All_Popular?>(null)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllPopularSeries()
    }

    val state by viewModel.allPopularSeries.collectAsState()

    when (state) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            isLoading = false
            val success = (state as ResultState.Success).success
            popularData = success
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Most Popular", color = Color.White)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0XFFb3d088)),
            actions = {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "", tint = Color.White
                )
            },
            windowInsets = WindowInsets(left = 23.dp)
        )
    }) { it ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFF1b1c18)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(), bottom = 80.dp)
                    .background(Color(0XFF1b1c18)),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                popularData?.results?.let {
                    items(it) { pop ->
                        PopularItem(popularResult = pop, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun PopularItem(
    popularResult: com.example.movie.tvseries.popular.PopularResult, navController: NavController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(21.dp))
                .width(190.dp).clickable {
                    val encodedTitle = Uri.encode(popularResult.originalName)
                    val date = Uri.encode(popularResult.firstAirDate)
                    val encodedDescription = Uri.encode(popularResult.overview)
                    val stars = Uri.encode(popularResult.popularity.toString())
                    val encodedPic = Uri.encode("https://image.tmdb.org/t/p/w342${popularResult.posterPath}")
                    val encodedVideo = Uri.encode("https://image.tmdb.org/t/p/w342${popularResult.backdropPath}")
                    navController.navigate(Screen.DetailScreen.route + "/${popularResult.id}/$encodedTitle/$encodedDescription/$encodedPic/$encodedVideo/$date/$stars")
                }
                .height(280.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${popularResult.posterPath}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .padding(top = 7.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .width(160.dp)
                    .align(Alignment.TopCenter)
                    .height(50.dp)
                    .background(Color.DarkGray.copy(alpha = 0.60f)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = Color(0XFFdba225)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(text = "${popularResult.popularity}", color = Color(0XFFdba225))

                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "-",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = popularResult.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    minLines = 1,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "",
                    tint = Color.White
                )

                Text(
                    text = "-",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = popularResult.firstAirDate,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "",
                    tint = Color.White
                )

                Text(
                    text = "-",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = popularResult.originalLanguage,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp, contentDescription = "", tint = Color.White
                )

                Text(
                    text = "-",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = "${popularResult.id}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "", tint = Color.White
                )

                Text(
                    text = "",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = popularResult.originalName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    minLines = 1,
                    maxLines = 1
                )
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Adult",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Text(
                    text = "-",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = "false",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }


        }
    }
}