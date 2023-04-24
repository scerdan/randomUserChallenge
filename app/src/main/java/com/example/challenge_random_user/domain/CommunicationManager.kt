package com.example.challenge_random_user.domain

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.permissions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class CommunicationManager {
    private fun sendEmailWithAttachment(
        context: Context,
        emailRecipient: String,
        emailImageUrl: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val imageLoader = ImageLoader.Builder(context)
                .crossfade(true)
                .build()
            val imageRequest = ImageRequest.Builder(context)
                .data(emailImageUrl)
                .build()
            val bitmap =
                (imageLoader.execute(imageRequest).drawable as BitmapDrawable).bitmap

            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "image.jpg")
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailRecipient))
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

    private fun callPhone(context: Context, phoneNumber: String) {
        val phoneNumberUri = "tel:$phoneNumber"
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse(phoneNumberUri)
        }
        context.startActivity(
            Intent.createChooser(
                intent,
                "Elige una app para realizar la llamada"
            )
        )
    }

    private fun saveImageFromUrlToGallery(context: Context, imageUrl: String, fileName: String) {
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
            val imageUri =
                MediaStore.Images.Media.insertImage(contentResolver, bitmap, fileName, "")
            val galleryIntent =
                Intent(Intent.ACTION_MEDIA_SCANNER_STARTED, Uri.parse(imageUri))
            context.sendBroadcast(galleryIntent)
        }
        Toast.makeText(context, "Imagen guardada en la galería", Toast.LENGTH_LONG).show()
    }


    @OptIn(ExperimentalPermissionsApi::class)
    fun checkPermission(
        permission: MultiplePermissionsState,
        context: Context,
        emailRecipient: String = "",
        emailImageUrl: String = "",
        phoneNumber: String = "",
        saveImageUrl: String = "",
        fileName: String = "",
        operation: Int
    ) {
        permission.permissions.forEach { typeOfPermission ->
            when (typeOfPermission.permission) {
                Manifest.permission.CALL_PHONE -> {
                    when {
                        typeOfPermission.status.isGranted -> {
                            if (operation == 0) {
                                callPhone(context, phoneNumber)
                            }
                        }
                        typeOfPermission.status.shouldShowRationale -> {}
                        else -> {}
                    }
                }
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    when {
                        typeOfPermission.status.isGranted -> {
                            if (operation == 1) {
                                saveImageFromUrlToGallery(context, saveImageUrl, fileName)
                            }
                        }
                        typeOfPermission.status.shouldShowRationale -> {}
                        else -> {}
                    }
                }
                Manifest.permission.READ_CONTACTS -> {
                    when {
                        typeOfPermission.status.isGranted -> {
                            if (operation == 2) {
                                sendEmailWithAttachment(context, emailRecipient, emailImageUrl)
                            }
                        }
                        typeOfPermission.status.shouldShowRationale -> {}
                        else -> {}
                    }
                }
            }
        }
    }

}
