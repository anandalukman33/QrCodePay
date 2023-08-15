package com.example.qrcodepay.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qrcodepay.ui.dialog.CustomAlertDialog
import com.example.qrcodepay.ui.theme.Blue700
import com.example.qrcodepay.ui.theme.White
import com.example.qrcodepay.util.rupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Halaman Riwayat Transaksi",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.getDataHistoryPayment() }) {
                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (!state.errorMessage.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = state.errorMessage, textAlign = TextAlign.Center)
                }
            } else if (state.isSuccess){
                Column(
                    modifier = Modifier.padding(it).clickable {
                        showCustomDialog = !showCustomDialog
                    }
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.response) { history ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                colors = CardDefaults.cardColors(Blue700)
                            ) {
                                Column {
                                    Text(
                                        text = history.merchantName,
                                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                                        style = TextStyle(
                                            fontStyle = FontStyle.Normal,
                                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            color = White
                                        )
                                    )
                                    Text(
                                        text = "Transaksi : "+rupiah(history.balance.toDouble()),
                                        modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 10.dp),
                                        style = TextStyle(
                                            fontStyle = FontStyle.Normal,
                                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                            fontWeight = FontWeight.Normal,
                                            color = White
                                        )
                                    )
                                }
                            }

                            if (showCustomDialog) {
                                CustomAlertDialog(
                                    { showCustomDialog = !showCustomDialog },
                                    { showCustomDialog = !showCustomDialog },
                                    history
                                    )
                            }
                        }
                    }
                }
            }
        }
    }


}
