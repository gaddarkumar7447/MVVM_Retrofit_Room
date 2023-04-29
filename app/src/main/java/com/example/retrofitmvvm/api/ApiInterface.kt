package com.example.retrofitmvvm.api

import com.example.retrofitmvvm.meemmodel.MemeData
import com.example.retrofitmvvm.meemmodel.MemeResponce
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/get_memes")
    suspend fun getJokes() : Response<MemeResponce>
}