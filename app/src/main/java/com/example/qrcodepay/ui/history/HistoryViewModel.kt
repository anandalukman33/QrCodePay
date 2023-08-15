package com.example.qrcodepay.ui.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcodepay.data.repository.DataRepository
import com.example.qrcodepay.ui.attribute.HistoryState
import com.example.qrcodepay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: DataRepository
): ViewModel() {


    val state = mutableStateOf(HistoryState())


    init {
        getDataHistoryPayment()
    }

    fun getDataHistoryPayment() {
        viewModelScope.launch {
            repository.getHistoryPayment().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state.value = state.value.copy(
                            isLoading = true,
                            isSuccess = false,
                            errorMessage = null
                        )
                    }

                    is Resource.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            response = resource.data ?: emptyList(),
                            isSuccess = true,
                            errorMessage = null
                        )
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            response = resource.data ?: emptyList(),
                            isSuccess = false,
                            errorMessage = resource.message
                        )
                    }

                    else -> {}
                }
            }
        }
    }

}