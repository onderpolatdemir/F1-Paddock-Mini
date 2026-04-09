package com.onder.f1PaddockMini.features.schedule.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.usecase.GetQualifyingResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QualifyingResultsViewModel @Inject constructor(
    private val getQualifyingResultUseCase: GetQualifyingResultUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    data class QualifyingResultsState(
        val qualifyingResults: List<QualifyingResult> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = ""
    )

    private val _state = MutableStateFlow(QualifyingResultsState())
    val state: StateFlow<QualifyingResultsState> = _state

    val year: String = savedStateHandle["year"] ?: ""
    val round: String = savedStateHandle["round"] ?: ""

    init {
        if (year.isNotEmpty() && round.isNotEmpty()) {
            getQualifyingResults(year, round)
        }
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
                    error = result.message ?: "An error occurred",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = result.isLoading)
            }
        }.launchIn(viewModelScope)
    }
}