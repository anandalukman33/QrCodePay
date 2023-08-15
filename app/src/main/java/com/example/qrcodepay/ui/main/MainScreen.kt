package com.example.qrcodepay.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.qrcodepay.R
import com.example.qrcodepay.ui.navigation.Screen
import com.example.qrcodepay.ui.theme.Blue700
import com.example.qrcodepay.ui.theme.White
import com.example.qrcodepay.util.rupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.balance?.name ?: "",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {viewModel.getMenus()}) {
                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .testTag("Box Main Screen")
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (!uiState.error.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = uiState.error, textAlign = TextAlign.Center)
                }
            } else if (uiState.isSuccess){

                Column {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 10.dp),
                        text = "Saldo anda ${rupiah(uiState.balance?.balance?.toLong()?.toDouble()!!) }",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    )

                    Card (
                        modifier = Modifier
                            .padding(start = 10.dp, end = 5.dp, top = 20.dp)
                            .width(110.dp)
                            .height(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Blue700),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Daftar Menu",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = White),
                            textAlign = TextAlign.Center,

                        )
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 10.dp),
                    ) {
                        items(uiState.menus!!) { data ->
                            if (data.isShowing) {
                                Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                                    when(data.id) {
                                        1 -> {
                                            //navController.popBackStack()
                                            navController.navigate(Screen.Qris.route + "/${uiState.balance.name}/${uiState.balance.balance.toLong()}")
//                                            {
//                                                popUpTo(Screen.Qris.route) { inclusive = true }
//                                            }
                                        }
                                        2 -> {
                                            //navController.popBackStack()
                                            navController.navigate(Screen.History.route)
//                                            {
//                                                popUpTo(Screen.History.route) { inclusive = true }
//                                            }
                                        }
                                    }
                                }) {
                                    Image(
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = null,
                                        painter = when (data.id) {
                                            1 -> {painterResource(id = R.drawable.ic_qris)}
                                            2 -> {painterResource(id = R.drawable.ic_history)}
                                            else -> { painterResource(id = R.drawable.ic_profile) }
                                        }

                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                    ) {
                                        Text(
                                            text = data.menu,
                                            style = TextStyle(
                                                fontStyle = FontStyle.Normal,
                                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                                textAlign = TextAlign.End,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = when (data.id) {
                                                1 -> {"Pembayaran Menggunakan QRIS"}
                                                2 -> {"Melihat Riwayat Transaksi"}
                                                else -> {"asd"}
                                                                  },
                                            style = TextStyle(
                                                fontStyle = FontStyle.Italic,
                                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                                                textAlign = TextAlign.End
                                            )
                                        )
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}