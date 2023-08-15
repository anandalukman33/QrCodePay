package com.example.qrcodepay.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.qrcodepay.ui.history.HistoryScreen
import com.example.qrcodepay.ui.main.BaseScreen
import com.example.qrcodepay.ui.qris.QrisScreen
import com.example.qrcodepay.ui.success.SuccessScreen
import com.example.qrcodepay.ui.transaction.TransactionScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@androidx.camera.core.ExperimentalGetImage
@Composable
fun NavigateScreens(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route,
        modifier = Modifier.padding(PaddingValues())) {

        composable(Screen.Home.route) {
            BaseScreen( navController = navController)
        }
        composable(Screen.Qris.route+ "/{name}/{currentBalance}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("currentBalance") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val currentBalance = backStackEntry.arguments?.getLong("currentBalance")
            QrisScreen(navController = navController, name = name.toString(), currentBalance = currentBalance ?: 0L)
        }
        composable(Screen.Transaction.route+ "/{bankDest}/{trxId}/{merchantName}/{balance}/{name}/{currentBalance}",
            arguments = listOf(
                navArgument("bankDest") { type = NavType.StringType },
                navArgument("trxId") { type = NavType.StringType },
                navArgument("merchantName") { type = NavType.StringType },
                navArgument("balance") { type = NavType.LongType },
                navArgument("name") { type = NavType.StringType },
                navArgument("currentBalance") { type = NavType.LongType }
            )
            ) { backStackEntry ->
            val bankDest = backStackEntry.arguments?.getString("bankDest")
            val trxId = backStackEntry.arguments?.getString("trxId")
            val merchantName = backStackEntry.arguments?.getString("merchantName")
            val balance = backStackEntry.arguments?.getLong("balance")
            val name = backStackEntry.arguments?.getString("name")
            val currentBalance = backStackEntry.arguments?.getLong("currentBalance")
            TransactionScreen(
                navController = navController,
                bankDest = bankDest.toString(),
                trxId = trxId.toString(),
                merchantName = merchantName.toString(),
                balance = balance ?: 0L,
                name = name.toString(),
                currentBalance = currentBalance ?: 0L
            )
        }

        composable(Screen.Success.route) {
            SuccessScreen(navController = navController)
        }

        composable(Screen.History.route) {
            HistoryScreen()
        }
    }
}