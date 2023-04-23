package com.example.challenge_random_user.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.presentation.UserState
import com.example.challenge_random_user.presentation.viewmodels.SharedViewmodel
import com.example.challenge_random_user.presentation.viewmodels.UserViewModel
import com.example.challenge_random_user.ui.theme.backMainColor
import com.example.challenge_random_user.utils.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserMainScreen(
    mainViewmodel: UserViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewmodel: SharedViewmodel
) {
    val viewModelState by mainViewmodel.state.collectAsState()

    PaintMainScreen(navController, sharedViewmodel, viewModelState, mainViewmodel)
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PaintMainScreen(
    navController: NavHostController,
    sharedViewmodel: SharedViewmodel,
    viewModel: UserState,
    mainViewmodel: UserViewModel
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val userList = viewModel.allUsers
    val lastIndex = userList.lastIndex
    val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

    Column(
        Modifier
            .fillMaxSize(1f)
            .background(backMainColor)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {

            if (lastVisibleIndex == lastIndex) {
                scope.launch(Dispatchers.Unconfined) {
                    mainViewmodel.fetchNextPageData()
                }
            }

            items(userList) { item ->
                Card(
                    Modifier
                        .fillMaxWidth(1.30f)
                        .fillMaxHeight(1f)
                        .padding(20.dp)
                        .clickable {
                            sharedViewmodel.addValue(item)
                            navController.navigate(Screen.DETAIL_SCREEN.route)
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = 15.dp
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(1f)
                    ) {
                        Column(
                            Modifier
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
                        Column(
                            Modifier.fillMaxWidth(1 / 1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = item.name.title + " " + item.name.first + " " + item.name.last)
                        }
                    }
                }
            }
        }
    }
}
