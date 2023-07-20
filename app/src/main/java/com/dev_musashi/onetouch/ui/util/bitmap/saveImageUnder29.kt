package com.dev_musashi.onetouch.ui.util.bitmap

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

fun saveImageUnder29(
    context: Context,
    captureImg: ImageBitmap,
    pictureImg: Bitmap,
    onError: (Throwable?) -> Unit
) {
    val externalStorage = Environment.getExternalStorageDirectory().absolutePath
    val fileName = System.currentTimeMillis().toString() + ".png"
    val path = "$externalStorage/DCIM/OneTouch"
    val dir = File(path)

    if (dir.exists().not()) {
        dir.mkdir()
    }

    try {
        val fileItem = File("$dir/$fileName").apply { this.createNewFile() }
        val fos = FileOutputStream(fileItem)
        CoroutineScope(Dispatchers.IO).launch {
            val captureImgBitmap = captureImg.asAndroidBitmap()
            val rotatedPicture = rotateBitmap(pictureImg, 90f)
            val resizedCapture =
                resizeBitmap(captureImgBitmap, rotatedPicture.width, rotatedPicture.height)
            val combineImg = combineBitmap(picture = rotatedPicture, capture = resizedCapture)
            combineImg.compress(Bitmap.CompressFormat.PNG, 100, fos)
            withContext(Dispatchers.IO) {
                fos.flush()
                fos.close()
            }
            context.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)
                )
            )
            onError(null)
        }
    } catch (e: Exception) {
        onError(e)
    }
}