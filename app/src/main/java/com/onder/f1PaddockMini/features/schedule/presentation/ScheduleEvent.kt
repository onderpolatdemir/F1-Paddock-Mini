package com.onder.f1PaddockMini.features.schedule.presentation

sealed class ScheduleEvent {
    data class YearChanged(val year: String) : ScheduleEvent()
    data class RaceExpanded(val raceId: String) : ScheduleEvent()
    data class NavigateToRaceResults(val year: String, val round: String) : ScheduleEvent()
    data class NavigateToQualifyingResults(val year: String, val round: String) : ScheduleEvent()
}