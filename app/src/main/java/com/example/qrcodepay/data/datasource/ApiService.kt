package com.example.qrcodepay.data.datasource

import com.example.qrcodepay.domain.model.BalanceResponse
import com.example.qrcodepay.domain.model.HistoryResponse
import com.example.qrcodepay.domain.model.Menus
import com.example.qrcodepay.domain.model.SuccessResponse
import com.example.qrcodepay.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.GET_DATA_CONFIG_MENU)
    suspend fun getQuotes() : Response<List<Menus>>

    @GET(Constants.GET_DATA_BALANCE_USER)
    suspend fun getBalances() : Response<BalanceResponse>

    @GET(Constants.SAVE_AND_UPDATE_DATA_QRIS_AND_USER)
    suspend fun saveDataQris(
        @Query("bankDest") bankDest: String,
        @Query("trxId") trxId: String,
        @Query("merchantName") merchantName: String,
        @Query("balance") balance: Long,
        @Query("name") name: String,
        @Query("currentBalance") currentBalance: Long,
        @Query("previousBalance") previousBalance: Long,
    ) : Response<SuccessResponse>

    @GET(Constants.GET_HISTORY_PAYMENT)
    suspend fun getDataHistoryPayment(): Response<List<HistoryResponse>>
}