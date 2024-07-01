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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import com.example.movie.people.People
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.PopularMovies
import com.example.movie.restapi.PopularResult
import com.example.movie.restapi.ResultState
import com.example.movie.tv.Result
import com.example.movie.tv.Tv
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: MainViewModel = koinInject()
    var movieData by remember { mutableStateOf<PopularMovies?>(null) }
    var tvData by remember { mutableStateOf<Tv?>(null) }
    val state by viewModel.allMovie.collectAsState()
    val tvState by viewModel.allTv.collectAsState()
    var searchBar by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var Loading by remember { mutableStateOf(false) }
    var peopleData by remember {
        mutableStateOf<People?>(null)
    }
    val peopleState by viewModel.allPeople.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllMovies()
        viewModel.getAllTv()
        viewModel.getPeople()
    }

    when (peopleState) {
        is ResultState.Error -> {
            Loading = false
            val error = (peopleState as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {
            Loading = true
        }

        is ResultState.Success -> {
            Loading = false
            val success = (peopleState as ResultState.Success).success
            peopleData = success
        }
    }

    when (tvState) {
        is ResultState.Error -> {
            Loading = false
            val error = (tvState as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {
            Loading = true

        }

        is ResultState.Success -> {
            Loading = false
            val success = (tvState as ResultState.Success).success
            tvData = success
        }
    }

    when (state) {
        is ResultState.Error -> {
            Loading = false
            val error = (state as ResultState.Error).error
            Text(text = error.toString(), color = Color.Red)
        }

        ResultState.Loading -> {
            Loading = true
        }

        is ResultState.Success -> {
            Loading = false
            movieData = (state as ResultState.Success).success
        }
    }

    val bannerImages = listOf(
        R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
        R.drawable.banner4, R.drawable.banner5, R.drawable.banner6,
        R.drawable.banner7, R.drawable.banner8,
    )

    val pagerState = rememberPagerState(pageCount = { bannerImages.size })
    LaunchedEffect(Unit) {
        launch {
            while (true) {
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % bannerImages.size
                pagerState.scrollToPage(nextPage)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(4.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Hello, Smith",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Let's Stream Your Favorite Movie",
                            color = Color.LightGray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W300
                        )
                    }
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.accountlogo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(53.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0XFF1b1c18)),
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(45.dp)
                            .background(Color.Gray.copy(alpha = 0.30f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Red
                        )
                    }

                }
            )
        }
    ) { it ->
        if (Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFF1b1c18)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFF1b1c18))
                    .padding(top = it.calculateTopPadding(), bottom = 100.dp)
            ) {
                item {
                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = {
                            Text(
                                text = "Search",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0XFF252836),
                            unfocusedContainerColor = Color(0XFF252836),
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(21.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "")
                        },
                        modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .height(51.dp),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalPager(
                        state = pagerState,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp)
                            .height(200.dp)
                    ) { page ->
                        BannerImage(bannerImages[page])
                    }

                    Spacer(modifier = Modifier.height(1.dp))

                    BannerDotsIndicator(pagerState)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Trending Now",
                            color = Color.White,
                            modifier = Modifier.padding(start = 7.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400
                        )

                        Text(
                            text = "See All",
                            color = Color(0XFF12cdd9),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W400
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0XFF1b1c18))
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        movieData?.results?.filter {
                            it.title.contains(search, ignoreCase = true)
                        }?.let {
                            items(it) { movie ->
                                MovieItem(result = movie, navController)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Popular Tv",
                            color = Color.White,
                            modifier = Modifier.padding(start = 7.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )

                        Text(
                            text = "See All",
                            color = Color(0XFF12cdd9),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0XFF1b1c18))
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        tvData?.results?.let {
                            items(it) { tv ->
                                TvItem(result = tv, navController = navController)
                            }
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Actors",
                            color = Color.White,
                            modifier = Modifier.padding(start = 7.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )

                        Text(
                            text = "See All",
                            color = Color(0XFF12cdd9),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0XFF1b1c18))
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        peopleData?.results?.let {
                            items(it) { people ->
                                PeopleItem(result = people)
                            }
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun MovieItem(result: PopularResult, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(160.dp)
            .clickable {
                val encodedTitle = Uri.encode(result.title)
                val date = Uri.encode(result.releaseDate)
                val encodedDescription = Uri.encode(result.overview)
                val stars = Uri.encode(result.popularity.toString())
                val encodedPic = Uri.encode("https://image.tmdb.org/t/p/w342${result.posterPath}")
                val encodedVideo = Uri.encode("https://image.tmdb.org/t/p/w342${result.backdropPath}")
                navController.navigate(Screen.DetailScreen.route + "/${result.id}/$encodedTitle/$encodedDescription/$encodedPic/$encodedVideo/$date/$stars")
            }
            .height(230.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(Color(0XFF1b1c18)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${result.backdropPath}",
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = result.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}


@Composable
fun TvItem(result: Result, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(160.dp)
            .clickable {
                val encodedTitle = Uri.encode(result.name)
                val date = Uri.encode(result.firstAirDate)
                val encodedDescription = Uri.encode(result.overview)
                val encodedPic = Uri.encode("https://image.tmdb.org/t/p/w342${result.backdropPath}")
                val encodedVideo = Uri.encode(result.voteAverage.toString())
                navController.navigate(Screen.DetailScreen.route + "/${result.id}/$encodedTitle/$encodedDescription/$encodedPic/$encodedVideo/$date")
            }
            .height(230.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(Color(0XFF1b1c18)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${result.backdropPath}",
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = result.name,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}

@Composable
fun PeopleItem(result: com.example.movie.people.Result) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(77.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            shape = CircleShape
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${result.profilePath}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = result.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            modifier = Modifier.aspectRatio(1f)
        )
    }
}


@Composable
fun BannerImage(imageResource: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0XFF15141F)),
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


