package com.example.qrcodepay.domain.model

data class HistoryResponse(

	val balance: Int,

	val bankDest: String,

	val currentBalance: Int,

	val name: String,

	val trxId: String,

	val merchantName: String,

	val previousBalance: Int
)
