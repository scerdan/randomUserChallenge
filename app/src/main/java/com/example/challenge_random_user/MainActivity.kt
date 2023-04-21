package com.example.challenge_random_user

import android.content.ClipData.Item
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.presentation.viewmodels.UserViewModel
import com.example.challenge_random_user.ui.theme.Challenge_random_userTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Challenge_random_userTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(mainViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Greeting(vModel: UserViewModel = hiltViewModel()) {
    val viewModel by vModel.state.collectAsState()
    val userList = viewModel.allUsers
    val myFavourites = userList.size


    LazyColumn(Modifier.fillMaxSize(1f)){
        items(myFavourites) {ite ->
            Card(
                Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1 / 10f)
                    .padding(top = 10.dp)
                    .background(Color.Gray)
            ) {

            }
        }
    }
}