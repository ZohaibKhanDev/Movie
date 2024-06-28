package com.example.movie.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movie.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Detail(
    navController: NavController,
    id: Int?,
    title: String?,
    des: String?,
    pic: String?,
    videoUrl: String?, // Use videoUrl instead of video
    date: String?
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
        }
    }
    var showPlay by remember {
        mutableStateOf(false)
    }
    val verticalScroll = rememberScrollState()
    Scaffold(topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable { navController.popBackStack() })
            }, actions = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .width(35.dp)
                        .height(35.dp)
                )
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll)
                .background(Color.Black)
                .padding(top = it.calculateTopPadding(), bottom = 80.dp),
        ) {

            if (showPlay) {
                AndroidView(
                    factory = {
                        StyledPlayerView(it).apply {
                            player = exoPlayer
                            useController = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                AsyncImage(
                    model = pic,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showPlay = true
                            exoPlayer.play()
                        }
                        .padding(10.dp)
                        .height(200.dp)
                )
            }

            Text(
                text = "$title",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp, top = 4.dp)
            )
            Text(text = "$date", modifier = Modifier.padding(10.dp), color = Color.White)

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    showPlay = true
                    exoPlayer.play()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(50.dp), shape = RoundedCornerShape(9.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "",
                        modifier = Modifier.size(35.dp)
                    )
                    Text(text = "Play", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.White, modifier = Modifier.size(45.dp)
                    )

                    Text(text = "My List", color = Color.White)
                }

                Spacer(modifier = Modifier.width(24.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "",
                        tint = Color.White,modifier = Modifier.size(45.dp)
                    )
                    Text(text = "Share", color = Color.White)
                }

                Spacer(modifier = Modifier.width(24.dp))

                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.LaptopWindows,
                        contentDescription = "",
                        tint = Color.White,modifier = Modifier.size(45.dp)
                    )
                    Text(text = "All Episodes", color = Color.White)
                }
            }

        }
    }


    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }
}
