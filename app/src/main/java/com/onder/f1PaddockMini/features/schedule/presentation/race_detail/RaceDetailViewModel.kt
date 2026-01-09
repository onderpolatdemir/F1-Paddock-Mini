//package com.onder.f1PaddockMini.features.schedule.presentation.race_detail
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.onder.f1PaddockMini.core.common.Resource
//import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
//import com.onder.f1PaddockMini.features.schedule.domain.model.RaceResult
//import com.onder.f1PaddockMini.features.schedule.domain.usecase.GetQualifyingResultUseCase
//import com.onder.f1PaddockMini.features.schedule.domain.usecase.GetRaceResultUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import javax.inject.Inject
//
//@HiltViewModel
//class RaceDetailViewModel @Inject constructor(
//    private val getRaceResultUseCase: GetRaceResultUseCase,
//    private val getQualifyingResultUseCase: GetQualifyingResultUseCase,
//    savedStateHandle: SavedStateHandle // Navigasyondan gelen argümanları tutar
//) : ViewModel() {
//
//    data class RaceDetailState(
//        val raceResults: List<RaceResult> = emptyList(),
//        val qualifyingResults: List<QualifyingResult> = emptyList(),
//        val isLoading: Boolean = false,
//        val error: String = "",
//        val filterType: String = "Race" // "Race" veya "Qualifying"
//    )
//
//    private val _state = MutableStateFlow(RaceDetailState())
//    val state: StateFlow<RaceDetailState> = _state
//
//    // Navigasyondan gelen parametreleri alıyoruz (Bunları NavGraph'ta tanımlayacağız)
//    val year: String = checkNotNull(savedStateHandle["year"])
//    val round: String = checkNotNull(savedStateHandle["round"])
//    // Kullanıcı hangi butona bastı? (Race Results mı Qualifying mi?)
//    val resultType: String = checkNotNull(savedStateHandle["type"])
//
//    init {
//        // Ekran açılır açılmaz veriyi çek
//        if (resultType == "race") {
//            getRaceResults(year, round)
//        } else {
//            getQualifyingResults(year, round)
//        }
//    }
//
//    private fun getRaceResults(year: String, round: String) {
//        getRaceResultUseCase(year, round).onEach { result ->
//            when(result) {
//                is Resource.Success -> _state.value = _state.value.copy(
//                    raceResults = result.data ?: emptyList(),
//                    isLoading = false,
//                    filterType = "Race"
//                )
//                is Resource.Error -> _state.value = _state.value.copy(
//                    error = result.message ?: "Hata",
//                    isLoading = false
//                )
//                is Resource.Loading -> _state.value = _state.value.copy(isLoading = result.isLoading)
//            }
//        }.launchIn(viewModelScope)
//    }
//
//    private fun getQualifyingResults(year: String, round: String) {
//        getQualifyingResultUseCase(year, round).onEach { result ->
//            when(result) {
//                is Resource.Success -> _state.value = _state.value.copy(
//                    qualifyingResults = result.data ?: emptyList(),
//                    isLoading = false,
//                    filterType = "Qualifying"
//                )
//                is Resource.Error -> _state.value = _state.value.copy(
//                    error = result.message ?: "Hata",
//                    isLoading = false
//                )
//                is Resource.Loading -> _state.value = _state.value.copy(isLoading = result.isLoading)
//            }
//        }.launchIn(viewModelScope)
//    }
//}

package com.onder.f1PaddockMini.features.schedule.presentation.race_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.model.RaceResult
import com.onder.f1PaddockMini.features.schedule.domain.usecase.GetQualifyingResultUseCase
import com.onder.f1PaddockMini.features.schedule.domain.usecase.GetRaceResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RaceDetailViewModel @Inject constructor(
    private val getRaceResultUseCase: GetRaceResultUseCase,
    private val getQualifyingResultUseCase: GetQualifyingResultUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    data class RaceDetailState(
        val raceResults: List<RaceResult> = emptyList(),
        val qualifyingResults: List<QualifyingResult> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = "",
        val filterType: String = "race" // "race" veya "qualifying" (küçük harf tutalım tutarlılık için)
    )

    private val _state = MutableStateFlow(RaceDetailState())
    val state: StateFlow<RaceDetailState> = _state

    // Navigasyondan gelen parametreler
    val year: String = checkNotNull(savedStateHandle["year"])
    val round: String = checkNotNull(savedStateHandle["round"])

    // İlk açılış tipi
    private val initialType: String = savedStateHandle["type"] ?: "race"

    init {
        // İlk açılışta gelen tipe göre yükle
        _state.value = _state.value.copy(filterType = initialType)
        if (initialType == "race") {
            getRaceResults(year, round)
        } else {
            getQualifyingResults(year, round)
        }
    }

    // YENİ EKLENEN KISIM: Kullanıcı etkileşimleri
    fun onEvent(event: RaceDetailEvent) {
        when(event) {
            is RaceDetailEvent.FilterChanged -> {
                // Eğer zaten o sekmedeysek tekrar yükleme yapma
                if (_state.value.filterType == event.type) return

                _state.value = _state.value.copy(filterType = event.type)

                // Hangi sekmeye geçildiyse onun verisini çek
                if (event.type == "race") {
                    getRaceResults(year, round)
                } else {
                    getQualifyingResults(year, round)
                }
            }
        }
    }

    private fun getRaceResults(year: String, round: String) {
        getRaceResultUseCase(year, round).onEach { result ->
            when(result) {
                is Resource.Success -> _state.value = _state.value.copy(
                    raceResults = result.data ?: emptyList(),
                    isLoading = false,
                    error = ""
                )
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "Hata",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = result.isLoading)
            }
        }.launchIn(viewModelScope)
    }

    private fun getQualifyingResults(year: String, round: String) {
        getQualifyingResultUseCase(year, round).onEach { result ->
            when(result) {
                is Resource.Success -> _state.value = _state.value.copy(
                    qualifyingResults = result.data ?: emptyList(),
                    isLoading = false,
                    error = ""
                )
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "Hata",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = result.isLoading)
            }
        }.launchIn(viewModelScope)
    }
}