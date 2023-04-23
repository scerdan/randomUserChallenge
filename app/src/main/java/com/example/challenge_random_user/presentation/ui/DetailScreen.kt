package com.example.challenge_random_user.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.presentation.viewmodels.SharedViewmodel
import com.example.challenge_random_user.ui.theme.GradientColor1
import com.example.challenge_random_user.ui.theme.backMainColor

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(viewModel: SharedViewmodel) {


    val dataNew = viewModel.clickedUser
    Log.e("USER =", dataNew.toString())

    val textUserData = arrayListOf<String>().apply {
        val address =
            dataNew?.location?.street?.name + dataNew?.location?.street?.number + " " + "${dataNew?.location?.city}" + " " + "${dataNew?.location?.state}" + " " + "${dataNew?.location?.postcode}"
        this.add(0, dataNew?.gender.toString())
        this.add(1, dataNew?.name?.title + " " + dataNew?.name?.first + " " + dataNew?.name?.last)
        this.add(2, dataNew?.login?.username.toString())
        this.add(3, dataNew?.email.toString())
        this.add(4, dataNew?.phone.toString())
        this.add(5, address)
    }



    Column(
        Modifier
            .fillMaxSize(1f)
            .background(backMainColor),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(5 / 10f)
                .padding(25.dp, 0.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 15.dp
        ) {
            Column() {
                Row(
                    Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        painter = rememberImagePainter(dataNew?.picture?.large),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null,
                    )
                }
                textUserData.forEach {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 5.dp, 0.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            color = GradientColor1
                        )
                    }
                }
            }
        }
    }


}