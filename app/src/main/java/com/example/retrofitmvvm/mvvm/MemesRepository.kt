package com.example.retrofitmvvm.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.meemmodel.Meme

class MemesRepository(private val apiInterface: ApiInterface) {

    private val memeLiveData = MutableLiveData<Meme>()

    val meme : LiveData<Meme>
    get() = memeLiveData

    suspend fun getMemes(){
        val result = apiInterface.getJokes()
        if (result.body() != null){
            memeLiveData.postValue(result.body())
        }
    }


}