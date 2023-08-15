package com.example.qrcodepay.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.qrcodepay.R
import com.example.qrcodepay.domain.model.HistoryResponse
import com.example.qrcodepay.ui.theme.Orange
import com.example.qrcodepay.ui.theme.White
import com.example.qrcodepay.util.rupiah

@Composable
fun CustomAlertDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    historyResponse: HistoryResponse? = null
) {

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(White)
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.Red.copy(alpha = 0.8F)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {

                    Image(
                        painter = painterResource(id = R.drawable.header_dialog_bni),
                        contentDescription = "Exit app",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    text = "ID Transaksi : ${historyResponse?.trxId ?: ""}",
                    modifier = Modifier.padding(12.dp), fontSize = 18.sp
                )

                Text(
                    text = "Saldo sebelumnya : ${rupiah(historyResponse?.previousBalance?.toDouble() ?: 0.0)}",
                    modifier = Modifier.padding(12.dp), fontSize = 18.sp
                )

                Text(
                    text = "Bank Tujuan : ${historyResponse?.bankDest ?: ""}",
                    modifier = Modifier.padding(12.dp), fontSize = 18.sp
                )

                Row(Modifier.padding(top = 10.dp)) {

                    Button(
                        onClick = { onExit() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(Orange)
                    ) {
                        Text(
                            text = "Tutup",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}