package com.hasan.nobetcieczanem.api

import com.ak.nobetcieczane.util.Constans.API_KEY
import com.hasan.nobetcieczanem.R
import com.hasan.nobetcieczanem.model.CityResponse
import io.reactivex.rxjava3.core.Single


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("health/dutyPharmacy")
    fun getCity(
        @Query("ilce") getIlce:String,
        @Query("il") getIl:String,
        @Header("authorization") api_key:String=API_KEY
    ): Single<CityResponse>

}