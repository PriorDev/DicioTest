package com.prior.diciotest.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.apache.commons.codec.binary.Base64

fun String.getImageFromBase64String(): ByteArray{
    var base64Image = this

    if(this.contains("data:image/jpeg;base64,")){
        base64Image = this.replace("data:image/jpeg;base64,", "")
    }

    return Base64.decodeBase64(base64Image)
}

fun String.toImageBitMap(): ImageBitmap?{
    try{
        val byteArray: ByteArray = this.getImageFromBase64String()

        val imageFormat = byteArray.getImageFormat()

        return if(imageFormat != null){
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            return bitmap.asImageBitmap()
        }else{
            null
        }
    }catch (e: Exception){
        Log.e("TAG", "toImageBitMap:", e)
        throw e
    }
}

private fun ByteArray.getImageFormat(): String? {
    val byteArray: ByteArray = this
    return when {
        byteArray.size >= 2 && byteArray[0] == 0xFF.toByte() && byteArray[1] == 0xD8.toByte() -> "JPEG" // JPEG format
        byteArray.size >= 8 && byteArray[0] == 0x89.toByte() && byteArray[1] == 0x50.toByte() && byteArray[2] == 0x4E.toByte() && byteArray[3] == 0x47.toByte() -> "PNG" // PNG format
        else -> null // Not a supported image format
    }
}