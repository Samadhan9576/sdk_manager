package com.samadhan.sdkmanager.domain.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.Poll
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.usecase.pole.GetPoleUseCase
import com.samadhan.sdkmanager.domain.event.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPoleUseCase: GetPoleUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(DashboardState())
    val uiState: State<DashboardState> = _uiState
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()
    var pole : List<Poll> = emptyList()
    init {
        getPole()
    }
    fun getPole() {
        viewModelScope.launch {
            val result = getPoleUseCase.invoke()
            when(result){
                is ServiceResult.Loading -> {
                    _uiState.value = DashboardState(isLoading = true)
                }
                is ServiceResult.Success -> {
                    Log.e("TAG", "getPole:${result.data} ", )
                   pole = result.data.content
                    _uiState.value = DashboardState(response = result.data, isLoading = false)
                }
                is ServiceResult.Error -> {
                    _uiState.value = DashboardState(error = "")
                    _events.send(LoginEvent.ShowSnackBar)
                }
            }
        }
    }
}

data class DashboardState(
    val isLoading: Boolean = false,
    val response: GetPollsResponse? = null,
    val error: String? = null
)
