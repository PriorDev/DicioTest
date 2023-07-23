package com.prior.diciotest.feature_registration.presentation

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior.diciotest.R
import com.prior.diciotest.core.Resource
import com.prior.diciotest.core.UiMessages
import com.prior.diciotest.core.UriToBase64String
import com.prior.diciotest.domain.SettingsRepository
import com.prior.diciotest.feature_registration.domain.models.DatosModel
import com.prior.diciotest.feature_registration.domain.models.RegistrationModel
import com.prior.diciotest.feature_registration.domain.RegistrationRepository
import com.prior.diciotest.feature_registration.domain.models.toApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val settingsRepository: SettingsRepository,
    private val uriToBase64String: UriToBase64String
): ViewModel() {
    private val _message = MutableStateFlow<UiMessages?>(null)
    val message = _message.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _registration = MutableStateFlow(RegistrationModel())
    val registration = _registration.asStateFlow()

    private val _datos = MutableStateFlow(DatosModel())
    val datos = _datos.asStateFlow()

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri = _uri.asStateFlow()

    private val regexAlphabetics = Regex("^[a-zA-Z]+\$")
    private val regexEmail = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$")

    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect{ settings ->
                settings?.let {
                    _uri.value = it.uri.toUri()
                }
            }
        }
    }

    fun onNameChange(text: String){
        _registration.value = registration.value.copy(
            nombre = if(regexAlphabetics.matches(text)) text else registration.value.nombre
        )
    }

    fun onPaternalNameChange(text: String){
        _registration.value = registration.value.copy(
            apellidoPaterno = if(regexAlphabetics.matches(text)) text else registration.value.apellidoPaterno
        )
    }

    fun onMaternalNameChange(text: String){
        _registration.value = registration.value.copy(
            apellidoMaterno = if(regexAlphabetics.matches(text)) text else registration.value.apellidoMaterno
        )
    }

    fun onEmailChange(text: String){
        _registration.value = registration.value.copy(
            email = text
        )
    }

    fun onBirthdayChange(text: String){
        _registration.value = registration.value.copy(
            fechaNac = text
        )
    }

    fun onStreetChange(text: String){
        _datos.value = datos.value.copy(
            calle = if(regexAlphabetics.matches(text)) text else datos.value.calle
        )
    }

    fun onNumberChange(text: String){
        _datos.value = datos.value.copy(
            numero = if(text.isDigitsOnly()) text else datos.value.numero
        )
    }

    fun onNeighborhoodChange(text: String){
        _datos.value = datos.value.copy(
            colonia = if(regexAlphabetics.matches(text)) text else datos.value.colonia
        )
    }

    fun onCityChange(text: String){
        _datos.value = datos.value.copy(
            delegacion = if(regexAlphabetics.matches(text)) text else datos.value.delegacion
        )
    }

    fun onStateChange(text: String){
        _datos.value = datos.value.copy(
            estado = if(regexAlphabetics.matches(text)) text else datos.value.estado
        )
    }

    fun onCPChange(text: String){
        _datos.value = datos.value.copy(
            cp = if(text.isDigitsOnly()) text else datos.value.cp
        )
    }

    private fun validateFields(): Int?{
        if(registration.value.nombre.isEmpty())
            return R.string.name_is_blank

        if(registration.value.apellidoPaterno.isEmpty())
            return R.string.paternal_last_name_is_blank

        if(registration.value.apellidoMaterno.isEmpty())
            return R.string.maternal_last_name_is_blank

        if(!regexEmail.matches(registration.value.email))
            return R.string.email_is_wrong

        if(registration.value.fechaNac.isEmpty())
            return R.string.birthday_is_blank

        if(datos.value.calle.isEmpty())
            return R.string.street_is_blank

        if(datos.value.numero.isEmpty())
            return R.string.number

        if(datos.value.colonia.isEmpty())
            return R.string.neighborhood_is_blank

        if(datos.value.delegacion.isEmpty())
            return R.string.city_is_blank

        if(datos.value.estado.isEmpty())
            return R.string.State_is_blank

        if(datos.value.cp.isEmpty())
            return R.string.cp_is_blank

        if(_uri.value == null)
            return R.string.take_a_photo_to_finish

        return null
    }

    fun onDismiss(){
        _message.value = UiMessages.DynamicMessage("")
    }

    fun onRegister() {
        val errorId = validateFields()
        if(errorId != null){
            _message.value = UiMessages.StringResource(errorId)
            return
        }

        viewModelScope.launch {
            _uri.value!!.let {
                val base64Image = uriToBase64String(it)

                if(base64Image is Resource.Error){
                    _message.value = base64Image.message
                }else{
                    _datos.value = datos.value.copy(imagen = base64Image.data!!)
                }
            }

            _registration.value = registration.value.copy(
                datos = datos.value.toApi()
            )

            registrationRepository.insertUser(registration.value).collect{ result ->
                when(result){
                    is Resource.Error -> {
                        _message.value = result.message
                    }
                    is Resource.Loading -> _isLoading.value = result.isLoading
                    is Resource.Success -> {
                        _message.value = UiMessages.StringResource(R.string.success_register)
                        Log.d(TAG, "onRegister: ${result.data.toString()}")
                        _registration.value = RegistrationModel()
                        _datos.value = DatosModel()
                        settingsRepository.delete()
                    }
                }
            }
        }
    }

    companion object{
        const val TAG = "RegistrationViewModel"
    }
}