package com.example.qrcodepay.domain.model

data class QrisData(

    var bankDest: String?,

    var trxId: String,

    var merchantName: String,

    var balance: Long
)