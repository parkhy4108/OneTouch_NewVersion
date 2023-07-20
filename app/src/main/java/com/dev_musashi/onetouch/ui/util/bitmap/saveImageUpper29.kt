package com.dev_musashi.onetouch.ui.util.bitmap

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.Q)
fun saveImageUpper29(
    context: Context,
    captureImg: ImageBitmap,
    pictureImg: Bitmap,
    onError: (Throwable?) -> Unit
) {
    val fileName = System.currentTimeMillis().toString() + ".png"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/OneTouch")
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }
    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )

    try {
        if (uri != null) {
            val img = context.contentResolver.openFileDescriptor(uri, "w", null)
            if (img != null) {
                val fos = FileOutputStream(img.fileDescriptor)
                CoroutineScope(Dispatchers.IO).launch {
                    val captureImgBitmap = captureImg.asAndroidBitmap()
                    val rotatedPicture = rotateBitmap(pictureImg, 90f)
                    val resizedCapture = resizeBitmap(
                        captureImgBitmap,
                        rotatedPicture.width,
                        rotatedPicture.height
                    )
                    val combineImg =
                        combineBitmap(picture = rotatedPicture, capture = resizedCapture)
                    combineImg.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    pictureImg.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    withContext(Dispatchers.IO) {
                        fos.flush()
                        fos.close()
                    }
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(uri, contentValues, null, null)
                    img.close()
                    onError(null)
                }
            }
        }
    } catch (e: Exception) {
        onError(e)
    }
}