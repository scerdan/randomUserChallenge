package com.example.challenge_random_user.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.challenge_random_user.presentation.viewmodels.SharedViewmodel
import com.example.challenge_random_user.ui.theme.GradientColor3
import com.example.challenge_random_user.ui.theme.backMainColor
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(viewModel: SharedViewmodel) {
    val context = LocalContext.current

    val dataNew = viewModel.clickedUser

    val textUserData = arrayListOf<String>().apply {
        val address =
            dataNew?.location?.street?.name + dataNew?.location?.street?.number + " " + "${dataNew?.location?.city}" + " " + "${dataNew?.location?.state}" + " " + "${dataNew?.location?.postcode}"
        this.add(0, dataNew?.gender.toString())
        this.add(1, dataNew?.name?.title + " " + dataNew?.name?.first + " " + dataNew?.name?.last)
        this.add(2, dataNew?.login?.username.toString())
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
                            sendEmailWithAttachment(
                                context,
                                dataNew?.email.toString(),
                                dataNew?.picture?.thumbnail.toString()
                            )
                        },
                        color = GradientColor3,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        text = dataNew?.phone.toString(),
                        modifier = Modifier.clickable {
                            callPhone(context, dataNew?.phone.toString())
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
    Toast.makeText(context, "Imagen guardada en la galería", Toast.LENGTH_LONG).show()
}

@SuppressLint("QueryPermissionsNeeded")
fun callPhone(context: Context, phoneNumber: String) {
    val phoneNumberUri = "tel:$phoneNumber"
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse(phoneNumberUri)
    }
    context.startActivity(Intent.createChooser(intent, "Elige una app para realizar la llamada"))
}

fun sendEmailWithAttachment(context: Context, recipient: String, imageUrl: String) {
    GlobalScope.launch(Dispatchers.IO) {
        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build()
        val imageRequest = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        val bitmap = (imageLoader.execute(imageRequest).drawable as BitmapDrawable).bitmap

        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "image.jpg")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.close()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(
                    context,
                    "${context.applicationContext.packageName}.provider",
                    file
                )
            )
            putExtra(Intent.EXTRA_SUBJECT, "Hola! Te adjunto la imagen solicitada")
            putExtra(Intent.EXTRA_TEXT, "Aguardo tu respuesta! :)")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "message/rfc822"
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        context.startActivity(Intent.createChooser(intent, "Enviar correo con:"))
    }
}

