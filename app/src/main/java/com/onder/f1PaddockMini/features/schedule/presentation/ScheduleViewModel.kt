package com.onder.f1PaddockMini.features.schedule.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.schedule.domain.model.Race
import com.onder.f1PaddockMini.features.schedule.domain.usecase.GetScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {

    // Ekranda gösterilecek state
    data class ScheduleState(
        val races: List<Race> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = "",
        val selectedYear: String = "2025"
    )

    private val _state = MutableStateFlow(ScheduleState())
    val state: StateFlow<ScheduleState> = _state

    init {
        loadSchedule("2025") // Varsayılan açılış
    }

    fun onEvent(event: ScheduleEvent) {
        when(event) {
            is ScheduleEvent.YearChanged -> {
                _state.value = _state.value.copy(selectedYear = event.year)
                loadSchedule(event.year)
            }
            is ScheduleEvent.RaceExpanded -> {
                val updatedRaces = _state.value.races.map { race ->
                    if (race.id == event.raceId) {
                        race.copy(isExpanded = !race.isExpanded)
                    } else {
                        race
                    }
                }
                _state.value = _state.value.copy(races = updatedRaces)
            }
            is ScheduleEvent.NavigateToRaceResults -> {
                // Navigation logic will be handled later
            }
            is ScheduleEvent.NavigateToQualifyingResults -> {
                // Navigation logic will be handled later
            }
        }
    }

    private fun loadSchedule(year: String) {
        getScheduleUseCase(year).onEach { result ->
            when(result) {
                is Resource.Success -> _state.value = _state.value.copy(
                    races = result.data ?: emptyList(),
                    isLoading = false
                )
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "Bilinmeyen hata",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = result.isLoading
                )
            }
        }.launchIn(viewModelScope)
    }
}