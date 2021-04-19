package com.lukianbat.feature.city.data.remote

import com.lukianbat.feature.city.data.remote.model.CitiesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("cities")
    fun getCities(@Query("namePrefix") namePrefix: String): Single<CitiesResponse>
}
