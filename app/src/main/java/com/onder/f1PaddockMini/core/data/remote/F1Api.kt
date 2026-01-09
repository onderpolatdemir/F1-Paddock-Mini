package com.onder.f1PaddockMini.core.data.remote

import com.onder.f1PaddockMini.features.drivers.data.remote.dto.DriverDto
import com.onder.f1PaddockMini.features.schedule.data.remote.dto.QualifyingDto
import com.onder.f1PaddockMini.features.schedule.data.remote.dto.RaceDto
import com.onder.f1PaddockMini.features.schedule.data.remote.dto.RaceResultDto
import com.onder.f1PaddockMini.features.standings.data.remote.dto.TeamStandingDto
import retrofit2.http.GET
import retrofit2.http.Path

interface F1Api {

    @GET("drivers/{year}")
    suspend fun getDrivers(@Path("year") year: String): List<DriverDto>

    @GET("schedule/{year}")
    suspend fun getSchedule(@Path("year") year: String): List<RaceDto>

    @GET("results/{year}/{round}")
    suspend fun getRaceResults(
        @Path("year") year: String,
        @Path("round") round: String
    ): List<RaceResultDto>

    @GET("qualifying/{year}/{round}")
    suspend fun getQualifyingResults(
        @Path("year") year: String,
        @Path("round") round: String
    ): List<QualifyingDto>

    @GET("standings/teams/{year}")
    suspend fun getTeamStandings(@Path("year") year: String): List<TeamStandingDto>

}