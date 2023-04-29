package com.example.retrofitmvvm.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvm.Utils.ApiResponce
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.database.DatabaseDaoInterface
import com.example.retrofitmvvm.meemmodel.Meme
import com.example.retrofitmvvm.meemmodel.MemeData
import com.example.retrofitmvvm.meemmodel.MemeResponce

class MemesRepository(private val apiInterface: ApiInterface, private val databaseDaoInterface: DatabaseDaoInterface) {

    private val memeMutableLiveDataMeme = MutableLiveData<ApiResponce<MemeResponce>>()
    val memeLiveDataMeme : LiveData<ApiResponce<MemeResponce>> = memeMutableLiveDataMeme

    suspend fun getMeme(){
        try {
            memeMutableLiveDataMeme.postValue(ApiResponce.Loading())
            val responce = apiInterface.getJokes().body()
            if (responce != null){
                memeMutableLiveDataMeme.postValue(ApiResponce.Successful(responce))
               databaseDaoInterface.insertMemeX(responce.data.memes)
            }else{
                memeMutableLiveDataMeme.postValue(ApiResponce.Error("Something went to wrong"))
            }
        }catch (e : Exception){
            memeMutableLiveDataMeme.postValue(ApiResponce.Error(e.toString()))
        }
    }
}
