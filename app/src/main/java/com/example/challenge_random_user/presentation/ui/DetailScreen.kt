package com.example.challenge_random_user.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.challenge_random_user.presentation.viewmodels.UserViewModel
import java.util.*

@Composable
fun DetailScreen(navBackStackEntry: NavBackStackEntry, viewModel: UserViewModel) {

    val dataRecieved = navBackStackEntry.arguments?.getStringArray("item")

    if (dataRecieved != null) {
        val arrayList = arrayListOf(dataRecieved)
        arrayList.map {
            Log.e("elemeto", it.toString())
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
//                Image(
//                    modifier = Modifier
//                        .size(50.dp)
//                        .align(Alignment.CenterVertically),
//                    painter = painterResource(""),
//                    contentDescription = null,
//                )
        }
        for (i in 2..7) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Text $i",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}