package com.dev_musashi.onetouch.data.gallery

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.dev_musashi.onetouch.R
import com.dev_musashi.onetouch.data.bitmap.combineBitmap
import com.dev_musashi.onetouch.data.bitmap.resizeBitmap
import com.dev_musashi.onetouch.data.bitmap.rotateBitmap
import com.dev_musashi.onetouch.domain.gallery.Gallery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class GalleryImpl @Inject constructor(
    private val context: Context
) : Gallery {

    override suspend fun saveImage(
        captureImg: ImageBitmap,
        pictureImg: ImageProxy
    ): Throwable? {
        return withContext(Dispatchers.IO) {
            val resource = context.resources
            val contentResolver = context.contentResolver
            val rotatedPicture = rotateBitmap(pictureImg.toBitmap(), 90f)
            val resizedCapture = resizeBitmap(captureImg.asAndroidBitmap(), rotatedPicture.width, rotatedPicture.height)
            val combinedBitmap = combineBitmap(picture1 = rotatedPicture, picture2 = resizedCapture)
            val fileName = System.currentTimeMillis().toString() + ".jpeg"
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/OneTouch")
            }
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    val externalStorage = Environment.getExternalStorageDirectory().absolutePath
                    val path = "$externalStorage/DCIM/OneTouch"
                    val dir = File(path)
                    if (dir.exists().not()) { dir.mkdir() }
                    val fileItem = File("$dir/$fileName").apply { this.createNewFile() }
                    val fos = FileOutputStream(fileItem)
                    combinedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                    @Suppress("DEPRECATION")
                    context.sendBroadcast(
                        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem))
                    )
                    null
                } else {
                    val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if(uri != null) {
                        contentResolver.openOutputStream(uri).use { outputStream ->
                            combinedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        }
                        contentValues.clear()
                        contentResolver.update(uri, contentValues, null, null)
                        null
                    } else {
                        throw Throwable(resource.getString(R.string.uriError))
                    }
                }
            } catch (e: Throwable) {
                e
            }
        }
    }
}
