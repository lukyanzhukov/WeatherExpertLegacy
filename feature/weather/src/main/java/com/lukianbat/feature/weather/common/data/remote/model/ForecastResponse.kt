package com.lukianbat.feature.weather.common.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val weatherItems: List<ListElement>
) {
    data class ListElement(
        @SerializedName("dt") val date: Long,
        @SerializedName("main") val main: Main,
        @SerializedName("wind") val wind: Wind,
        @SerializedName("weather") val weather: List<Weather>,
    )

    data class Main(
        @SerializedName("temp") val temp: Double,
        @SerializedName("feels_like") val feelsLike: Double,
        @SerializedName("temp_min") val minTemp: Double,
        @SerializedName("temp_max") val maxTemp: Double,
        @SerializedName("humidity") val humidity: Int,
    )

    data class Wind(
        @SerializedName("speed") val windSpeed: Double,
    )

    data class Weather(@SerializedName("main") val description: String)
}