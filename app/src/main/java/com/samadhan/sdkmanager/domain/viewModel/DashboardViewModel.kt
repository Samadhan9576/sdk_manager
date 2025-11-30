package com.samadhan.sdkmanager.domain.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.PoleDetails.PollDetailsResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.Constants
import com.samadhan.sdk.domain.usecase.pole.GetPoleDetailsUseCase
import com.samadhan.sdk.domain.usecase.pole.GetPoleUseCase
import com.samadhan.sdk.domain.usecase.pole.GetUserSelectedOptionsUseCase
import com.samadhan.sdk.domain.usecase.pole.SavePoleUseCase
import com.samadhan.sdkmanager.domain.event.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPoleUseCase: GetPoleUseCase,
    private val getPoleDetailsUseCase: GetPoleDetailsUseCase,
    private val getUserSelectedOptionsUseCase: GetUserSelectedOptionsUseCase,
    private val savePoleUseCase: SavePoleUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(DashboardState())
    val uiState: State<DashboardState> = _uiState

    private val _uiDetailsState = mutableStateOf(DetailsState())
    val uiDetailsState: State<DetailsState> = _uiDetailsState
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()
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
                    _uiState.value = DashboardState(response = result.data, isLoading = false)
                }
                is ServiceResult.Error -> {
                    _uiState.value = DashboardState(error = "")
                    _events.send(LoginEvent.ShowSnackBar)
                }
            }
        }
    }

    fun getPoleDetail(id:Int,clickId:Int) {
        viewModelScope.launch {
            val result = getPoleDetailsUseCase.invoke(id)
            when(result){
                is ServiceResult.Loading -> {
                    _uiDetailsState.value = DetailsState(isLoading = true)

                }
                is ServiceResult.Success -> {
                    _events.send(LoginEvent.PoleDetailSuccess(clickId,id))
                    _uiDetailsState.value = DetailsState(isLoading = false,result.data)
                    Log.e("TAG", "getPole:${result.data} ", )

                }
                is ServiceResult.Error -> {
                    _events.send(LoginEvent.ShowSnackBar)
                    _uiDetailsState.value = DetailsState(error = "")

                }
            }
        }
    }
    fun getUserSelectedOptions(id:Int) {
        viewModelScope.launch {
            val result = getUserSelectedOptionsUseCase.invoke(id)
            when(result){
                is ServiceResult.Loading -> {

                }
                is ServiceResult.Success -> {
                    Constants.id = id
//                    Log.e("TAG", "getPole:${result.data} ", )
                }
                is ServiceResult.Error -> {
                    _events.send(LoginEvent.ShowSnackBar)

                }
            }
        }
    }
    fun savePole(poleId:Int) {
        viewModelScope.launch {
            val result = savePoleUseCase.invoke(Constants.id,poleId)
            when(result){
                is ServiceResult.Loading -> {

                }
                is ServiceResult.Success -> {
//                    Log.e("TAG", "getPole:${result.data} ", )
                }
                is ServiceResult.Error -> {
                    _events.send(LoginEvent.ShowSnackBar)

                }
            }
        }
    }
    fun removePole(poleId:Int) {
        viewModelScope.launch {
            val result = savePoleUseCase.removePole(poleId)
            when(result){
                is ServiceResult.Loading -> {

                }
                is ServiceResult.Success -> {
//                    Log.e("TAG", "getPole:${result.data} ", )
                }
                is ServiceResult.Error -> {
                    _events.send(LoginEvent.ShowSnackBar)

                }
            }
        }
    }
    fun submitQR(QR:String) {
        viewModelScope.launch {
            val result = savePoleUseCase.scanPole(
                QR = QR
            )
            when(result){
                is ServiceResult.Loading -> {

                }
                is ServiceResult.Success -> {
//                    Log.e("TAG", "getPole:${result.data} ", )
                }
                is ServiceResult.Error -> {
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
data class DetailsState(
    val isLoading: Boolean = false,
    val response: PollDetailsResponse? = null,
    val error: String? = null
)
