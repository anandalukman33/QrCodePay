package com.example.qrcodepay.ui.transaction

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.qrcodepay.ui.MainActivity
import com.example.qrcodepay.ui.navigation.Screen
import com.example.qrcodepay.ui.qris.QrisViewModel
import com.example.qrcodepay.ui.theme.Blue700
import com.example.qrcodepay.ui.theme.White
import com.example.qrcodepay.util.rupiah

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@androidx.camera.core.ExperimentalGetImage
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    navController: NavController,
    viewModel: QrisViewModel = hiltViewModel(),
    bankDest: String,
    trxId: String,
    merchantName: String,
    balance: Long,
    name: String,
    currentBalance: Long
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                    text = "Halaman Transaksi",
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    )
                )
            })
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(150.dp),
                    colors = CardDefaults.cardColors(containerColor = Blue700),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Row (
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Row {
                                Text(modifier = Modifier.width(100.dp), text = "Merchant", style = TextStyle(fontWeight = FontWeight.Bold, color = White))
                                Text(text = " : ", style = TextStyle(color = White))
                                Text(text = merchantName, style = TextStyle(color = White))
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Text(modifier = Modifier.width(100.dp), text = "Nominal Transaksi", style = TextStyle(fontWeight = FontWeight.Bold, color = White))
                                Text(text = " : ", style = TextStyle(color = White))
                                Text(text = rupiah(balance.toDouble()), style = TextStyle(color = White))
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Text(modifier = Modifier.width(100.dp), text = "ID Transaksi", style = TextStyle(fontWeight = FontWeight.Bold, color = White))
                                Text(text = " : ", style = TextStyle(color = White))
                                Text(text = trxId, style = TextStyle(color = White))
                            }
                        }

                    }
                }

                //Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (viewModel.state.value.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                        } else if (viewModel.state.value.error != null) {
                            Toast.makeText(context, viewModel.state.value.error, Toast.LENGTH_SHORT).show()
                        } else if (viewModel.state.value.isSuccess) {
                            LaunchedEffect(Unit) {
                                //navController.popBackStack()
                                navController.navigate(Screen.Success.route) {
                                    popUpTo(Screen.Success.route) { inclusive = true }
                                }
                            }

                        }

                        ShowDialog(viewModel, bankDest, trxId, merchantName, balance, name, currentBalance)
                    }
                //}
            }
        }
    }
}

@Composable
fun ShowDialog(
    viewModel: QrisViewModel = hiltViewModel(),
    bankDest: String,
    trxId: String,
    merchantName: String,
    balance: Long,
    name: String,
    currentBalance: Long
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.padding(16.dp)) {

        val showDialog = remember { mutableStateOf(false)  }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 50.dp, end = 50.dp),
            onClick = { showDialog.value = true }

        ) {
            Text(text = "Bayar", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Reminder!") },
                text = { Text("Apa anda yakin ingin melakukan transaksi?") },
                confirmButton = {
                    TextButton(onClick = {
                    /* TODO */
                        val nowBalance = currentBalance - balance
                        viewModel.saveDataTransaction(
                            bankDest, trxId, merchantName, balance, name, nowBalance, currentBalance
                        )
                        showDialog.value = false
                    }) {
                        Text("Bayar".uppercase())
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("Kembali".uppercase())
                    }
                },
            )
        }
    }
}