package com.example.challenge_random_user.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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

    Column(
        Modifier
            .fillMaxSize(1f)
            .background(Color.White)
    ) {
        LazyColumn(Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally) {
            items(userList) { item ->
                Card(
                    Modifier
                        .fillMaxWidth(1.55f)
                        .padding(20.dp)
                        .clickable { },
                    shape = RoundedCornerShape(12.dp),
                    elevation = 15.dp
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(1f)
                    ) {
                        Column(Modifier
                            .fillMaxWidth(1 / 3f)
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                                    .aspectRatio(16f / 9f),
                                painter = rememberImagePainter(data = item.picture.medium),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                            )
                        }
                        Column(Modifier.fillMaxWidth(1 / 1f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = item.name.title + " " + item.name.first + " " + item.name.last)
                        }
                    }


                }
            }
        }
    }

}