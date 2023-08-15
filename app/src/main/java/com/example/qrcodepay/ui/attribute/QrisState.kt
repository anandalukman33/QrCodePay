package com.example.qrcodepay.ui.attribute

import com.example.qrcodepay.domain.model.QrisData

data class QrisState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val qrisData: QrisData? = null,
    val isSuccess: Boolean = false
)