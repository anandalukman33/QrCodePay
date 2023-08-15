package com.example.qrcodepay.ui.qris

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcodepay.data.repository.DataRepository
import com.example.qrcodepay.domain.model.QrisData
import com.example.qrcodepay.ui.attribute.QrisState
import com.example.qrcodepay.ui.attribute.UiState
import com.example.qrcodepay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrisViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val state = mutableStateOf(QrisState())

    fun saveDataTransaction(bankDest: String, trxId: String, merchantName: String, balance: Long, name: String, currentBalance: Long, previousBalance: Long) {
        viewModelScope.launch {
            repository.saveDataTrx(bankDest, trxId, merchantName, balance, name, currentBalance, previousBalance).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state.value = state.value.copy(
                            isLoading = true,
                            error = null,
                            isSuccess = false
                        )
                    }

                    is Resource.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            error = null,
                            isSuccess = true
                        )
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            error = resource.message,
                            isSuccess = false
                        )
                    }
                }
            }
        }
    }
}