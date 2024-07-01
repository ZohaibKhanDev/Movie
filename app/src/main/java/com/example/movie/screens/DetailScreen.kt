package com.example.movie.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movie.bottomnavigation.Screen
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.ResultState
import com.example.movie.similer.Result
import com.example.movie.similer.Similar
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Detail(
    navController: NavController,
    id: Int?,
    title: String?,
    des: String?,
    pic: String?,
    videoUrl: String?,
    date: String?,
    star: String?
) {
    val viewModel: MainViewModel = koinInject()
    var similarData by remember {
        mutableStateOf<Similar?>(null)
    }
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        viewModel.similarMovies()
    }
    val state by viewModel.allSimilar.collectAsState()
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
            similarData = success
        }
    }

    val verticalScroll = rememberScrollState()
    val context = LocalContext.current
    var showPlay by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = title.toString(), color = Color.White) }, navigationIcon = {
            Icon(imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.clickable { navController.popBackStack() })
        }, actions = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.DarkGray.copy(alpha = 0.50f))
                    .size(45.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
            }

        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0XFF1b1c18))
        )
    }) { it ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFF1b1c18)), contentAlignment = Alignment.BottomCenter
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScroll)
                    .background(Color(0XFF1b1c18))
                    .padding(top = it.calculateTopPadding(), bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0XFF1b1c18))
                        .height(500.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = videoUrl,
                        contentDescription = "",
                        contentScale = Crop,
                        modifier = Modifier.fillMaxSize()
                    )


                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        AsyncImage(
                            model = pic,
                            contentDescription = "",
                            contentScale = Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(21.dp))
                                .width(360.dp)
                                .height(300.dp)
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "",
                                    tint = Color.White
                                )

                                Text(
                                    text = "$date",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                                )

                                VerticalDivider(thickness = 6.dp, modifier = Modifier.height(26.dp))
                                Spacer(modifier = Modifier.width(10.dp))

                            }


                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = "",
                                    tint = Color.White
                                )

                                Text(
                                    text = "148 Minutes",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                                )

                                VerticalDivider(thickness = 6.dp, modifier = Modifier.height(26.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                            }


                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MovieFilter,
                                    contentDescription = "",
                                    tint = Color.White
                                )

                                Text(
                                    text = "Action",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                                )

                            }
                        }


                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(11.dp))
                                .width(120.dp)
                                .background(Color(0XFF44483d))
                                .height(45.dp), contentAlignment = Alignment.Center
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "",
                                    tint = Color(0XFFff8700)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(text = "$star", fontSize = 17.sp, fontWeight = FontWeight.W500)
                            }
                        }


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(21.dp))
                                    .width(120.dp)
                                    .background(Color(0XFFff8700))
                                    .height(50.dp), contentAlignment = Alignment.Center
                            ) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(text = "Play", color = Color.White)
                                }
                            }


                            Spacer(modifier = Modifier.width(17.dp))


                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(56.dp)
                                    .background(Color(0XFF252836)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "",
                                    tint = Color(0XFFff8700)
                                )
                            }


                            Spacer(modifier = Modifier.width(17.dp))


                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(56.dp)
                                    .background(Color(0XFF252836)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = "",
                                    tint = Color(0XFF12cdd9)
                                )
                            }

                        }
                    }

                }

                Text(text = "$des", color = Color.White, modifier = Modifier.padding(10.dp))


                Spacer(modifier = Modifier.height(26.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Similar",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Text(
                        text = "See All",
                        color = Color(0XFF12cdd9),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    similarData?.results?.let {
                        items(it) {
                            SimilarMovies(result = it)
                        }
                    }
                }
            }


        }


    }

}

@Composable
fun SimilarMovies(result: Result) {
    Card(
        modifier = Modifier
            .clickable {
            }
            .padding(8.dp)
            .width(160.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${result.posterPath}",
                contentDescription = result.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = result.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )
        }
    }
}