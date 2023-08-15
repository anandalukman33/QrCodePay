package com.example.qrcodepay.ui.attribute

import com.example.qrcodepay.domain.model.HistoryResponse

data class HistoryState (
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val response: List<HistoryResponse> = emptyList()
)