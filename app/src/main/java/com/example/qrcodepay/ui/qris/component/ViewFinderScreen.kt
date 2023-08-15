package com.example.qrcodepay.ui.qris.component

import android.media.MediaPlayer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.qrcodepay.R
import com.example.qrcodepay.di.BarcodeAnalyser
import com.example.qrcodepay.domain.model.QrisData
import com.example.qrcodepay.ui.navigation.Screen
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable
fun ViewFinderScannerScreen(
    navController: NavController,
    name: String,
    currentBalance: Long
) {
    // Fetching the local context
    val mContext = LocalContext.current

    AndroidView({ context ->
        val cameraExecutor = Executors.newSingleThreadExecutor()
        val previewView = PreviewView(context).also {
            it.scaleType = PreviewView.ScaleType.FILL_CENTER
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val imageCapture = ImageCapture.Builder().build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyser { scannerOutput ->
                        val dataQris = QrisData("","","", 0L)
                        if (scannerOutput != null) {
                            if (scannerOutput.contains(".")) {
                                val arrayString = scannerOutput.split(".").toTypedArray()

                                for (i in arrayString.indices) {
                                    dataQris.bankDest = arrayString[0]
                                    dataQris.trxId = arrayString[1]
                                    dataQris.merchantName = arrayString[2]
                                    dataQris.balance = arrayString[3].toLong()
                                }

                            }
                        }

                        // Declaring and Initializing
                        // the MediaPlayer to play "audio.mp3"
                            val mMediaPlayer = MediaPlayer.create(mContext, R.raw.scan)
                            mMediaPlayer.start()
                        //navController.popBackStack()
                            navController.navigate(Screen.Transaction.route+ "/${dataQris.bankDest}/${dataQris.trxId}/${dataQris.merchantName}/${dataQris.balance}/$name/$currentBalance")
                            {
                                popUpTo(Screen.Transaction.route) { inclusive = true }
                            }
                        cameraExecutor.shutdown()
                        mMediaPlayer.release()
                        //Toast.makeText(context, "Barcode found ${dataQris.bankDest}, ${dataQris.trxId}, ${dataQris.merchantName}, ${dataQris.balance}", Toast.LENGTH_SHORT).show()
                        //viewModel.saveDataTransaction(dataQris.bankDest, dataQris.trxId, dataQris.merchantName, dataQris.balance)
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context as ComponentActivity, cameraSelector, preview, imageCapture, imageAnalyzer)

            } catch(exc: Exception) {
                Log.e("DEBUG", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
        previewView
    },
        modifier = Modifier
            .fillMaxSize() )
}