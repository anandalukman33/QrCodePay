package com.example.qrcodepay.ui.navigation

sealed class Screen(val route: String){
    object Home: Screen(route = "home")
    object Qris: Screen(route = "qris")
    object Transaction: Screen(route = "transaction")
    object Success: Screen(route = "success")
    object History: Screen(route = "history")
}