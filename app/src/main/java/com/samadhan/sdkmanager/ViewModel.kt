package com.samadhan.sdkmanager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.data.model.User
import com.samadhan.sdk.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(UserUiState())
    val uiState: State<UserUiState> = _uiState

    init {
        fetchUser("1")
    }
    private fun fetchUser(id: String) {
        viewModelScope.launch {
            val result = getUserUseCase.invoke(id)
            when(result){
                is ServiceResult.Loading -> { }
                is ServiceResult.Success -> {
                    _uiState.value = UserUiState(
                        isLoading = false,
                        user = result.data
                    )
                }
                is ServiceResult.Error -> {}
            }
        }
    }
}

data class UserUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)