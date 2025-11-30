package com.samadhan.sdkmanager.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.usecase.pole.PoleLoginUseCase
import com.samadhan.sdkmanager.domain.event.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PoleLoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun login(email:String, password:String, remember:Boolean) {
        viewModelScope.launch {
            val result = loginUseCase.invoke(LoginRequest(email,password,remember))
            when(result){
                is ServiceResult.Loading -> {
                    _events.send(LoginEvent.isLoading)
                    _uiState.value = LoginState(isLoading = true)
                }
                is ServiceResult.Success -> {
                    _events.send(LoginEvent.OnLoginSuccess)
                    _uiState.value = LoginState(response = result.data, isLoading = false)
                }
                is ServiceResult.Error -> {
                    _uiState.value = LoginState(error = "")
                    _events.send(LoginEvent.ShowSnackBar)
                }
            }
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val response: LoginResponse? = null,
    val error: String? = null
)