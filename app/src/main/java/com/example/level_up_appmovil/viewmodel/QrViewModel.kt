package com.example.level_up_appmovil.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QrViewModel : ViewModel() {
    private val _qrResult = MutableStateFlow<String?>(null)
    val qrResult: StateFlow<String?> = _qrResult.asStateFlow()

    fun onQrDetected(result: String) {
        _qrResult.value = result
    }

    fun clearResult() {
        _qrResult.value = null
    }
}