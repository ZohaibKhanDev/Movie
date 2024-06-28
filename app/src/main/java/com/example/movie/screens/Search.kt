package com.example.movie.screens

import android.graphics.Movie
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movie.restapi.MainViewModel
import com.example.movie.restapi.Movies
import com.example.movie.restapi.Result
import org.koin.compose.koinInject
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController,) {
    val viewModel:MainViewModel= koinInject()
    var search by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Result>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = search) {
        if (search.isNotEmpty()) {
            isLoading = true
            val results = viewModel.getAllMovies()
            isLoading = false
        } else {
            searchResults = emptyList()
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable { navController.popBackStack() })

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
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black
                    )
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .background(Color(0XFF000000)),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(searchResults){
                    MovieItem(result = it, navController)
                }
            }
        }
    }
}