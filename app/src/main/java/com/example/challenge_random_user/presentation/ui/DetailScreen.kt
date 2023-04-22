package com.example.challenge_random_user.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.presentation.viewmodels.SharedViewmodel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(viewModel: SharedViewmodel) {


    val dataNew = viewModel.clickedUser
    Log.e("USER =", dataNew.toString())

    val textUserData = arrayListOf<String>().apply {
        val address = dataNew?.location?.street?.name + dataNew?.location?.street?.number +" "+"${dataNew?.location?.city}"+" "+"${dataNew?.location?.state}"+" "+"${dataNew?.location?.postcode}"
        this.add(0, dataNew?.gender.toString())
        this.add(1, dataNew?.name?.title + " "+ dataNew?.name?.first + " "+ dataNew?.name?.last)
        this.add(2, dataNew?.login?.username.toString())
        this.add(3, dataNew?.email.toString())
        this.add(4, dataNew?.phone.toString())
        this.add(5, address)
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
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterVertically),
                    painter = rememberImagePainter(dataNew?.picture?.medium),
                    contentDescription = null,
                )
        }
        for (i in textUserData) {
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