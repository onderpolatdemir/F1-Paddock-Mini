//package com.onder.f1PaddockMini.features.drivers.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.onder.f1PaddockMini.core.common.Resource
//import com.onder.f1PaddockMini.features.drivers.domain.model.Driver
//import com.onder.f1PaddockMini.features.drivers.domain.usecase.GetDriversUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import javax.inject.Inject
//
//@HiltViewModel
//class DriversViewModel @Inject constructor(
//    private val getDriversUseCase: GetDriversUseCase
//) : ViewModel() {
//
//    // Drivers State (Ekranda görünecek veri yapısı)
//    data class DriversState(
//        val drivers: List<Driver> = emptyList(),
//        val isLoading: Boolean = false,
//        val error: String = "",
//        val selectedYear: String = "2025"
//    )
//
//    private val _state = MutableStateFlow(DriversState())
//    val state: StateFlow<DriversState> = _state
//
//    init {
//        loadDrivers("2025")
//    }
//
//    fun onEvent(event: DriversEvent) {
//        when(event) {
//            is DriversEvent.YearChanged -> {
//                if (_state.value.selectedYear == event.year) return
//                _state.value = _state.value.copy(selectedYear = event.year)
//                loadDrivers(event.year)
//            }
//        }
//    }
//
//    private fun loadDrivers(year: String) {
//        getDriversUseCase(year).onEach { result ->
//            when (result) {
//                is Resource.Success -> _state.value = _state.value.copy(
//                    drivers = result.data ?: emptyList(),
//                    isLoading = false
//                )
//                is Resource.Error -> _state.value = _state.value.copy(
//                    error = result.message ?: "Hata",
//                    isLoading = false
//                )
//                is Resource.Loading -> _state.value = _state.value.copy(
//                    isLoading = result.isLoading
//                )
//            }
//        }.launchIn(viewModelScope)
//    }
//}

package com.onder.f1PaddockMini.features.drivers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.drivers.domain.usecase.GetDriversUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(
    private val getDriversUseCase: GetDriversUseCase // Repository YERİNE UseCase geldi
) : ViewModel() {

    private val _state = MutableStateFlow(DriversState())
    val state: StateFlow<DriversState> = _state

    init {
        // Uygulama açılınca varsayılan olarak 2024'ü çeksin
        loadDrivers("2024")
    }

    fun onEvent(event: DriversEvent) {
        when(event) {
            is DriversEvent.YearChanged -> {
                // Eğer yıl aynıysa tekrar istek atma
                if (_state.value.selectedYear == event.year) return

                // State'deki yılı güncelle ve veriyi çek
                _state.value = _state.value.copy(selectedYear = event.year)
                loadDrivers(event.year)
            }
        }
    }

    private fun loadDrivers(year: String) {
        // UseCase'i invoke ediyoruz (parantez açıp çağırıyoruz)
        getDriversUseCase(year).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        drivers = result.data ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Beklenmedik hata",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Resource.Loading içinden boolean geliyorsa onu kullan
                    // Gelmiyorsa direkt true verebilirsin
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}