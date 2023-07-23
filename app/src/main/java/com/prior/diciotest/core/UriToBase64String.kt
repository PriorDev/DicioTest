package com.prior.diciotest.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.prior.diciotest.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

class UriToBase64String @Inject constructor(
    @ApplicationContext val context: Context
) {
    operator fun invoke(uri: Uri): Resource<String>{
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap == null) {
                return Resource.Error(UiMessages.StringResource(R.string.unable_to_convert_photo))
            }

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

            return Resource.Success(Base64.encodeToString(byteArray, Base64.NO_WRAP))
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(UiMessages.StringResource(R.string.unable_to_convert_photo))
        }
    }
}