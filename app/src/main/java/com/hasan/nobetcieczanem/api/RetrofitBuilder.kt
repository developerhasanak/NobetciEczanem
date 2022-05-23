package com.hasan.nobetcieczanem.api

import com.ak.nobetcieczane.util.Constans
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitAPI= Retrofit.Builder()
        .baseUrl(Constans.BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(RetrofitAPI::class.java)

}