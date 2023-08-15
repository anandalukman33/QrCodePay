package com.example.qrcodepay.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcodepay.data.repository.DataRepository
import com.example.qrcodepay.ui.attribute.UiState
import com.example.qrcodepay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val uiState = mutableStateOf(UiState())

    init {
        getMenus()
    }

    fun getMenus() {
        viewModelScope.launch {
            repository.getNotes().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        uiState.value = uiState.value.copy(
                            isLoading = true,
                            isSuccess = false,
                            error = null
                        )
                    }

                    is Resource.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            menus = resource.data,
                            isSuccess = false,
                            error = null
                        )
                        getBalances()
                    }

                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            menus = resource.data,
                            isSuccess = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    private fun getBalances() {
        viewModelScope.launch {
            repository.getBalance().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        uiState.value = uiState.value.copy(
                            isLoading = true,
                            error = null,
                            isSuccess = false,
                        )
                    }

                    is Resource.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            balance = resource.data,
                            error = null,
                            isSuccess = true,
                        )
                    }

                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            balance = resource.data,
                            error = resource.message,
                            isSuccess = false,
                        )
                    }
                }
            }
        }
    }
}