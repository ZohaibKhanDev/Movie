package com.example.movie.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movie.R
import com.example.movie.bottomnavigation.Screen
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.Movies
import com.example.movie.restapi.Result
import com.example.movie.restapi.ResultState
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: MainViewModel = koinInject()
    var movieData by remember { mutableStateOf<Movies?>(null) }
    val state by viewModel.allMovie.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getAllMovies()
    }
    var searchBar by remember {
        mutableStateOf(false)
    }
    var search by remember {
        mutableStateOf("")
    }
    var Loading by remember { mutableStateOf(false) }

    when (state) {
        is ResultState.Error -> {
            Loading = false
            val error = (state as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {
            Loading = true
        }

        is ResultState.Success -> {
            Loading = false
            val success = (state as ResultState.Success).success
            movieData = success
        }
    }


    Scaffold(topBar = {
        TopAppBar(title = {
            if (searchBar) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.clickable { searchBar = false })

                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier
                            .padding(7.dp)
                            .fillMaxWidth()
                            .height(52.dp),
                        placeholder = {
                            Text(
                                text = "Search Movie", fontSize = 20.sp
                            )
                        },
                        textStyle = TextStyle(fontSize = 20.sp),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black
                        )
                    )
                }

            }
        }, navigationIcon = {
            if (searchBar) {

            } else {
                Image(
                    painter = painterResource(id = R.drawable.navigationicon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(49.dp)
                        .height(49.dp)
                )
            }

        }, actions = {
            if (searchBar) {

            } else {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .width(35.dp)
                        .clickable { searchBar = true }
                        .height(35.dp),
                    tint = Color.White)
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(35.dp)
                        .height(35.dp)
                )
            }

        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }) {
        if (Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .background(Color(0XFF000000)), horizontalAlignment = Alignment.Start
            ) {

                val bannerImages = listOf(
                    R.drawable.banner1, R.drawable.banner2, R.drawable.banner3
                )
                val pagerState = rememberPagerState(pageCount = { bannerImages.size })

                HorizontalPager(
                    state = pagerState,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .wrapContentWidth()
                        .padding(start = 15.dp, end = 15.dp)
                        .height(200.dp)
                ) { page ->
                    BannerImage(bannerImages[page])
                }

                Spacer(modifier = Modifier.height(1.dp))
                BannerDotsIndicator(pagerState)

                Text(
                    text = "Trending Now",
                    color = Color.White,
                    modifier = Modifier.padding(start = 7.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    movieData?.results?.filter {
                        it.title.contains(search, ignoreCase = true)
                    }?.let {
                        items(it) { movie ->
                            MovieItem(result = movie, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(result: Result, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(100.dp)
            .clickable {
                val encodedTitle = Uri.encode(result.title)
                val date = Uri.encode(result.releaseDate)
                val encodedDescription = Uri.encode(result.overview)
                val encodedPic = Uri.encode("https://image.tmdb.org/t/p/w342${result.backdropPath}")
                val encodedVideo = Uri.encode(result.video.toString())
                navController.navigate(Screen.DetailScreen.route + "/${result.id}/$encodedTitle/$encodedDescription/$encodedPic/$encodedVideo/$date")
            }
            .height(210.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            Color.Black
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w342${result.backdropPath}",
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun BannerImage(imageResource: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerDotsIndicator(pagerState: androidx.compose.foundation.pager.PagerState) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}
