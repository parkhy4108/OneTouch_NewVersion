package com.dev_musashi.onetouch

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import com.dev_musashi.onetouch.R.string as AppText

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val permissions =  arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher.launch(permissions)
        setContent { MainContents() }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionList ->
        permissionList.forEach { permission ->
            if (permission.key == Manifest.permission.CAMERA && !permission.value) {
                SnackBarManager.showMessage(AppText.CameraPermissionDenied)
            }
            if (permission.key == Manifest.permission.READ_EXTERNAL_STORAGE && !permission.value) {
                SnackBarManager.showMessage(AppText.StoragePermissionDenied)
            }
            if (permission.key == Manifest.permission.WRITE_EXTERNAL_STORAGE && !permission.value) {
                SnackBarManager.showMessage(AppText.StoragePermissionDenied)
            }
        }
    }

}


