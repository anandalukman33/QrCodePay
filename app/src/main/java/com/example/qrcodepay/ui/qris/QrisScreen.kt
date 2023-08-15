package com.example.qrcodepay.ui.qris

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.qrcodepay.ui.qris.component.ViewFinderScannerScreen

@Composable
@androidx.camera.core.ExperimentalGetImage
fun QrisScreen(
    navController: NavHostController,
    name: String,
    currentBalance: Long
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                ViewFinderScannerScreen(navController,name,currentBalance)
            }
        }
    }

}