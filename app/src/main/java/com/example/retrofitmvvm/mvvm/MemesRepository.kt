package com.example.retrofitmvvm.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvm.Utils.Utilities
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.meemmodel.Meme

class MemesRepository(private val apiInterface: ApiInterface, private val context: Context) {


    private val memeLiveData = MutableLiveData<Meme>()

    val meme : LiveData<Meme>
    get() = memeLiveData

    suspend fun getMemes() {
        val result = apiInterface.getJokes()
        if (result.body() != null) {
            memeLiveData.postValue(result.body())

             /*if (Utilities.isNetworkAvailable(context)){
                instance.getDataDao().insertMemeX(result.body()!!.data.memes)
            }*/
        }

        }
    }
