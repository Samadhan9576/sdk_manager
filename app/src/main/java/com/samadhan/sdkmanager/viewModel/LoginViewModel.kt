package com.samadhan.sdkmanager.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.usecase.pole.PoleLoginUseCase
import com.samadhan.sdkmanager.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PoleLoginUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginState())
    val uiState: State<LoginState> = _uiState

    fun login(email:String, password:String, remember:Boolean) {
        viewModelScope.launch {
            val result = loginUseCase.invoke(LoginRequest(email,password,remember))
            when(result){
                is ServiceResult.Loading -> {

                }
                is ServiceResult.Success -> {
                    _uiState.value = LoginState(response = result.data)
                }
                is ServiceResult.Error -> {}
            }
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val response: LoginResponse? = null,
    val error: String? = null
)