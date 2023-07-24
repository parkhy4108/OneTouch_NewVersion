package com.dev_musashi.onetouch

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import com.dev_musashi.onetouch.R.string as AppText

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val buildVersion = Build.VERSION.SDK_INT
    private val permissions =  arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(permissions)

        setContent { MainContents() }

        setFullScreen(buildVersion)
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
        }
    }




    private fun setFullScreen(buildVersion: Int) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        @RequiresApi(Build.VERSION_CODES.S)
        if (buildVersion < Build.VERSION_CODES.R) {
            actionBar?.hide()
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        } else {
            window.setDecorFitsSystemWindows(false)
            val windowController = window.insetsController
            if (windowController != null) {
                windowController.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
                windowController.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

}


