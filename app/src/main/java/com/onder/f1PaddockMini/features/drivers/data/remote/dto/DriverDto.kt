package com.onder.f1PaddockMini.features.drivers.data.remote.dto
import com.google.gson.annotations.SerializedName

data class DriverDto(
    // Backend: "driver_id" -> Kotlin: driverId
    @SerializedName("driver_id")
    val driverId: String,

    @SerializedName("driver_name")
    val driverName: String,

    @SerializedName("driver_number")
    val driverNumber: String,

    @SerializedName("nationality")
    val nationality: String,

    @SerializedName("team_name")
    val teamName: String,

    // Takım ID'sini belki ilerde logo çekmek için kullanırız
    @SerializedName("team_id")
    val teamId: String,

    @SerializedName("position")
    val position: String,

    @SerializedName("points")
    val points: String
)