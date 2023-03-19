package com.example.retrofitmvvm.meemmodel

import com.google.gson.annotations.SerializedName

//jokes
data class Meme(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)