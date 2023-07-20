package com.dev_musashi.onetouch

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.dev_musashi.onetouch.ui.util.snackBar.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import com.dev_musashi.onetouch.R.string as AppText

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val buildVersion = Build.VERSION.SDK_INT
        requestPermissionLauncher.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ))

        setContent { MainContents() }

        fullHideStatusBar(buildVersion)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionList ->
        permissionList.forEach { isGranted ->
            if(!isGranted.value) {
                SnackBarManager.showMessage(AppText.CameraPermissionDenied)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun fullHideStatusBar(buildVersion: Int){
        if(buildVersion >= Build.VERSION_CODES.M && buildVersion < Build.VERSION_CODES.R){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            actionBar?.hide()
        } else if(buildVersion >= Build.VERSION_CODES.R){
            window.setDecorFitsSystemWindows(false)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val windowController = window.insetsController
            if (windowController != null) {
                windowController.hide(WindowInsets.Type.systemBars())
                windowController.systemBarsBehavior = if(buildVersion >= Build.VERSION_CODES.S) WindowInsetsController.BEHAVIOR_DEFAULT else WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

}


