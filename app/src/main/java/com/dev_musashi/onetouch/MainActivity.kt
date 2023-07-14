package com.dev_musashi.onetouch

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dev_musashi.onetouch.uiLayer.util.snackBar.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import com.dev_musashi.onetouch.R.string as AppText
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        super.onCreate(savedInstanceState)
        setContent {
            MainContents()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            SnackBarManager.showMessage(AppText.CameraPermissionGranted)
        } else {
            SnackBarManager.showMessage(AppText.CameraPermissionDenied)
        }
    }



}


