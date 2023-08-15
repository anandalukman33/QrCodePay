package com.example.qrcodepay.ui.success

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qrcodepay.R
import com.example.qrcodepay.ui.MainActivity
import com.example.qrcodepay.ui.navigation.Screen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@androidx.camera.core.ExperimentalGetImage
@OptIn(ExperimentalAnimationGraphicsApi::class)

@Composable
fun SuccessScreen(navController: NavController) {

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val image = AnimatedImageVector.animatedVectorResource(id = R.drawable.avd_check_uncheck)
        var atEnd by remember {
            mutableStateOf(false)
        }
        val painterFirst = rememberAnimatedVectorPainter(animatedImageVector = image, atEnd = atEnd)
        val painterSecond = rememberAnimatedVectorPainter(animatedImageVector = image, atEnd = !atEnd)

        val isRunning by remember { mutableStateOf(true) }

        suspend fun runAnimation() {
            while (isRunning) {
                atEnd = !atEnd
                delay(7000)
            }
        }
        LaunchedEffect(image) {
            runAnimation()
        }
            Column(
                modifier =
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(5.dp),
                    painter = if (atEnd) painterFirst else painterSecond,
                    contentDescription = null
                )

                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 4.dp,
                        end = 16.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Terima Kasih",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    Text(
                        text = "Pembayaran Anda Berhasil",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Button(onClick = {
                                //navController.popBackStack()
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route) { inclusive = true }
                                }

                        }) {
                            Text(text = "Kembali")
                        }
                    }


                }
            }
    }
}