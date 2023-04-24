package com.example.challenge_random_user.presentation.ui

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.challenge_random_user.domain.CommunicationManager
import com.example.challenge_random_user.presentation.viewmodels.SharedViewmodel
import com.example.challenge_random_user.ui.theme.GradientColor3
import com.example.challenge_random_user.ui.theme.backMainColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalCoilApi::class, ExperimentalPermissionsApi::class)
@Composable
fun DetailScreen(viewModel: SharedViewmodel) {
    val context = LocalContext.current
    val dataNew = viewModel.clickedUser
    val communicationManager = CommunicationManager()
    val textUserData = arrayListOf<String>().apply {
        val address =
            dataNew?.location?.street?.name + dataNew?.location?.street?.number + " " + "${dataNew?.location?.city}" + " " + "${dataNew?.location?.state}" + " " + "${dataNew?.location?.postcode}"
        this.add(0, dataNew?.gender.toString())
        this.add(1, dataNew?.name?.title + " " + dataNew?.name?.first + " " + dataNew?.name?.last)
        this.add(2, dataNew?.login?.username.toString())
        this.add(3, address)
    }

    val allPermission = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
        )
    )

    LaunchedEffect(key1 = true) {
        allPermission.launchMultiplePermissionRequest()
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
                .fillMaxHeight(6 / 10f)
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
                            .aspectRatio(1f)
                            .clickable {
                                communicationManager.checkPermission(
                                    permission = allPermission,
                                    context = context,
                                    saveImageUrl = dataNew?.picture?.large.toString(),
                                    fileName = dataNew?.login?.username.toString(),
                                    operation = 1
                                )
                            },
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
                            color = Color.Black,
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth(1f)
                        .padding(0.dp, 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = dataNew?.email.toString(),
                        modifier = Modifier.clickable {
                            communicationManager.checkPermission(
                                permission = allPermission,
                                context = context,
                                emailRecipient = dataNew?.email.toString(),
                                emailImageUrl = dataNew?.picture?.thumbnail.toString(),
                                operation = 2
                            )
                        },
                        color = GradientColor3,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        text = dataNew?.phone.toString(),
                        modifier = Modifier.clickable {
                            communicationManager.checkPermission(
                                permission = allPermission,
                                context = context,
                                phoneNumber = dataNew?.phone.toString(),
                                operation = 0
                            )
                        },
                        color = GradientColor3,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}