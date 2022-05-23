package com.hasan.nobetcieczanem.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("result")
    val result:List<CityResult>
)
