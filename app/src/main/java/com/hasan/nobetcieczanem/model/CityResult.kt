package com.hasan.nobetcieczanem.model

import com.google.gson.annotations.SerializedName

data class CityResult(
    @SerializedName("name")
    val name:String,
    @SerializedName("dist")
    val dist:String,
    @SerializedName("address")
    val address:String,
    @SerializedName("phone")
    val phone:String,
    @SerializedName("loc")
    val loc:String
    
)
