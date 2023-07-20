package com.dev_musashi.onetouch.data.gallery

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.dev_musashi.onetouch.domain.gallery.Gallery
import com.dev_musashi.onetouch.ui.util.bitmap.combineBitmap
import com.dev_musashi.onetouch.ui.util.bitmap.resizeBitmap
import com.dev_musashi.onetouch.ui.util.bitmap.rotateBitmap
import com.dev_musashi.onetouch.ui.util.bitmap.saveImageUnder29
import com.dev_musashi.onetouch.ui.util.bitmap.saveImageUpper29
import com.dev_musashi.onetouch.ui.util.snackBar.SnackBarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class Gallery @Inject constructor(
    private val context: Context
) : Gallery {

    override suspend fun saveImage(
        captureImg: ImageBitmap,
        pictureImg: Bitmap,
        onError: (Throwable?) -> Unit
    ) {
        try {
            if (Build.VERSION.SDK_INT < 29) {
                saveImageUnder29(
                    context,
                    captureImg = captureImg,
                    pictureImg = pictureImg
                ) { exception ->
                    if(exception != null) throw exception
                }
            } else {
                saveImageUpper29(
                    context,
                    captureImg = captureImg,
                    pictureImg = pictureImg,
                ) { exception ->
                    if(exception != null) throw exception
                }
            }
            onError(null)
        } catch (e: Throwable) {
            onError(e)
        }
    }

}