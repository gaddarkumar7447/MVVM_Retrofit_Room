package com.example.retrofitmvvm.Utils

sealed class ApiResponce<T>(val data : T?= null, val message : String ?= null) {
    class Loading<T> : ApiResponce<T>()
    class Successful<T>(apiData : T)  : ApiResponce<T>(data = apiData)
    class Error<T>(errorMessage : String) : ApiResponce<T>(message = errorMessage)

}