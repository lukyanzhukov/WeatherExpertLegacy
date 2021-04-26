package com.lukianbat.feature.weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weatherDescription: List<WeatherDescription>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
) {

    data class WeatherDescription(
        @SerializedName("description") val description: String,
        @SerializedName("icon") val icon: String
    )

    data class Main(
        @SerializedName("temp") val temp: Double,
        @SerializedName("feels_like") val feelsLike: Double,
        @SerializedName("temp_min") val tempMin: Double,
        @SerializedName("temp_max") val tempMax: Double,
        @SerializedName("humidity") val humidity: Int
    )

    data class Wind(
        @SerializedName("speed") val speed: Double,
    )
}
