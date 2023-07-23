package com.prior.diciotest.feature_users.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior.diciotest.core.Resource
import com.prior.diciotest.core.UiMessages
import com.prior.diciotest.feature_users.domain.UsersRepository
import com.prior.diciotest.domain.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(
    private val repository: UsersRepository
): ViewModel() {
    private val _message = MutableStateFlow<UiMessages?>(null)
    val message = _message.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _userData = mutableStateListOf<UserData>()
    val userList: List<UserData> = _userData

    init {
        viewModelScope.launch {
            repository.getUsers().collect{ result ->
                when(result){
                    is Resource.Error -> {
                        _message.value = result.message
                    }
                    is Resource.Loading -> {
                        _isLoading.value = result.isLoading
                    }
                    is Resource.Success -> {
                        _userData.clear()
                        _userData.addAll(result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun onDismiss(){
        _message.value = UiMessages.DynamicMessage("");
    }
}