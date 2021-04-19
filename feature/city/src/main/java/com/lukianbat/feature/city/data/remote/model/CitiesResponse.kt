package com.lukianbat.feature.city.data.remote.model

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("data")
    val cities: List<CityResponseModel>,
) {
    data class CityResponseModel(
        @SerializedName("city")
        val city: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("countryCode")
        val countryCode: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("regionCode")
        val regionCode: String,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double
    )
}
