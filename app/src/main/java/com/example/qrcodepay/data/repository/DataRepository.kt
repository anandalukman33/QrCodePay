package com.example.qrcodepay.data.repository

import com.example.qrcodepay.data.datasource.ApiService
import com.example.qrcodepay.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getNotes() = flow {
        emit(Resource.Loading())
        val api = api.getQuotes().body()
        emit(Resource.Success(api))
    }.catch { error ->
        emit(Resource.Error(error.message!!))
    }

    suspend fun getBalance() = flow {
        emit(Resource.Loading())
        val api = api.getBalances().body()
        emit(Resource.Success(api))
    }.catch { error ->
        emit(Resource.Error(error.message!!))
    }

    suspend fun saveDataTrx(bankDest: String, trxId: String, merchantName: String, balance: Long, name: String, currentBalance: Long, previousBalance: Long) = flow {
        emit(Resource.Loading())
        val successQrisSave = api.saveDataQris(bankDest, trxId, merchantName, balance, name, currentBalance, previousBalance).body()
        emit(Resource.Success(successQrisSave))
    }.catch { error ->
        emit(Resource.Error(error.message!!))
    }

    suspend fun getHistoryPayment() = flow {
        emit(Resource.Loading())
        val api = api.getDataHistoryPayment().body()
        emit(Resource.Success(api))
    }.catch { error ->
        emit(Resource.Error(error.message!!))
    }

}
