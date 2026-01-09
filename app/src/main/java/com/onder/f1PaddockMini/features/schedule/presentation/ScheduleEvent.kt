package com.onder.f1PaddockMini.features.schedule.presentation

sealed class ScheduleEvent {
    data class YearChanged(val year: String) : ScheduleEvent()
    // İleride "Yarışa tıklandı" gibi eventler eklersek buraya gelecek
}