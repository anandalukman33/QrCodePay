package com.example.qrcodepay.util

import java.text.NumberFormat
import java.util.Locale


fun rupiah(number: Double): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    return numberFormat.format(number).toString()
}
