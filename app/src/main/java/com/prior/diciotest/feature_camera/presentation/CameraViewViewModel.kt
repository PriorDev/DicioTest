package com.prior.diciotest.feature_camera.presentation

import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior.diciotest.core.UiMessages
import com.prior.diciotest.data.database.toDB
import com.prior.diciotest.domain.SettingsRepository
import com.prior.diciotest.domain.models.SettingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewViewModel @Inject constructor(
    private val repository: SettingsRepository
): ViewModel() {
    private val _message = MutableStateFlow<UiMessages?>(null)
    val message = _message.asStateFlow()

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri = _uri.asStateFlow()

    fun onDismiss() {
        _message.value = null
    }

    fun onError(ex: ImageCaptureException) {
        _message.value = UiMessages.DynamicMessage(ex.message ?: "")
    }

    fun onPhotoTook(uri: Uri){
        _uri.value = uri
    }

    fun onDiscard(){
        _uri.value = null
    }

    fun onSavePhoto(){
        viewModelScope.launch {
            val setting = SettingsData(id = 1, uri = _uri.value.toString())
            repository.insert(setting.toDB())
        }
    }
}
