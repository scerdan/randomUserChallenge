package com.example.challenge_random_user.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.presentation.viewmodels.UserViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserMainScreen(vModel: UserViewModel = hiltViewModel()) {

    val viewModel by vModel.state.collectAsState()
    val userList = viewModel.allUsers


    LazyColumn(Modifier.fillMaxSize(1f)) {
        items(userList) { item ->
            Card(
                Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1 / 10f)
                    .padding(top = 10.dp)
                    .background(Color.Gray)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    painter = rememberImagePainter(data = item.picture.large),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}