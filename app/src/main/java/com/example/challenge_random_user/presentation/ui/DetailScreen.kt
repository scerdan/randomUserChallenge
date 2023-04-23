package com.example.challenge_random_user.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.challenge_random_user.presentation.viewmodels.SharedViewmodel
import com.example.challenge_random_user.ui.theme.GradientColor1
import com.example.challenge_random_user.ui.theme.backMainColor
import kotlinx.coroutines.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(viewModel: SharedViewmodel) {
    val context = LocalContext.current


    val dataNew = viewModel.clickedUser
    Log.e("USER =", dataNew.toString())

    val textUserData = arrayListOf<String>().apply {
        val address =
            dataNew?.location?.street?.name + dataNew?.location?.street?.number + " " + "${dataNew?.location?.city}" + " " + "${dataNew?.location?.state}" + " " + "${dataNew?.location?.postcode}"
        this.add(0, dataNew?.gender.toString())
        this.add(1, dataNew?.name?.title + " " + dataNew?.name?.first + " " + dataNew?.name?.last)
        this.add(2, dataNew?.login?.username.toString())
//        this.add(3, dataNew?.email.toString())
//        this.add(3, dataNew?.phone.toString())
        this.add(3, address)
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
                                saveImageFromUrlToGallery(
                                    context,
                                    dataNew?.picture?.large.toString(),
                                    "primeraFoto"
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
                            color = GradientColor1
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth(1f)
                        .padding(0.dp, 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = dataNew?.email.toString(),
                        Modifier.clickable {
                            sendEmail(context, dataNew?.email.toString())
                        })
                    Text(text = dataNew?.phone.toString(),
                        Modifier.clickable {
                            callPhone(context, dataNew?.phone.toString())
                        })
                }

            }
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun sendEmail(context: Context, recipient: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        this.addCategory(Intent.CATEGORY_DEFAULT)
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        putExtra(Intent.EXTRA_SUBJECT, "subject");
        putExtra(Intent.EXTRA_TEXT, "body");
    }

    context.startActivity(Intent.createChooser(intent,"Elige una app para enviar el correo"))
}

@OptIn(DelicateCoroutinesApi::class)
fun saveImageFromUrlToGallery(context: Context, imageUrl: String, fileName: String) {
    GlobalScope.launch(Dispatchers.IO) {
        val imageLoader = ImageLoader.Builder(context)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        val result = (imageLoader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        val contentResolver = context.contentResolver
        val imageUri = MediaStore.Images.Media.insertImage(contentResolver, bitmap, fileName, "")
        val galleryIntent = Intent(Intent.ACTION_MEDIA_SCANNER_STARTED, Uri.parse(imageUri))
        context.sendBroadcast(galleryIntent)
    }

    Toast.makeText(context, "Imagen guardada en la galería", Toast.LENGTH_SHORT).show()
}

@SuppressLint("QueryPermissionsNeeded")
fun callPhone(context: Context, phoneNumber: String) {
    val phoneNumberUri = "tel:$phoneNumber"
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse(phoneNumberUri)
    }
    context.startActivity(Intent.createChooser(intent, "Elige una app para realizar la llamada"))
}
