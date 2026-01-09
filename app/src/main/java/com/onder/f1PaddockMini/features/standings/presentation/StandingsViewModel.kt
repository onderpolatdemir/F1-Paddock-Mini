package com.onder.f1PaddockMini.features.standings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.drivers.presentation.DriversEvent
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding
import com.onder.f1PaddockMini.features.standings.domain.usecase.GetTeamStandingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val getTeamStandingsUseCase: GetTeamStandingsUseCase
) : ViewModel() {

    data class StandingsState(
        val standings: List<TeamStanding> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = "",
        val selectedYear: String = "2025"
    )

    private val _state = MutableStateFlow(StandingsState())
    val state: StateFlow<StandingsState> = _state

    init {
        loadStandings("2025")
    }

//    fun onYearChanged(year: String) {
//        if (_state.value.selectedYear == year) return
//        _state.value = _state.value.copy(selectedYear = year)
//        loadStandings(year)
//    }

    fun onEvent(event: StandingsEvent) {
        when(event) {
            is StandingsEvent.YearChanged -> {
                if (_state.value.selectedYear == event.year) return
                _state.value = _state.value.copy(selectedYear = event.year)
                loadStandings(event.year)
            }
        }
    }

    private fun loadStandings(year: String) {
        getTeamStandingsUseCase(year).onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = _state.value.copy(
                    standings = result.data ?: emptyList(),
                    isLoading = false
                )
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "Hata",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = result.isLoading
                )
            }
        }.launchIn(viewModelScope)
    }
}