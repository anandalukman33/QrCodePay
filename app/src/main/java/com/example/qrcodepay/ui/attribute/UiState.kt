package com.example.qrcodepay.ui.attribute

import com.example.qrcodepay.domain.model.BalanceResponse
import com.example.qrcodepay.domain.model.Menus

data class UiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val menus: List<Menus>? = emptyList(),
    val balance: BalanceResponse? = null
)
