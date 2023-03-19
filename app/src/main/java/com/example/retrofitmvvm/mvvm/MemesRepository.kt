package com.example.retrofitmvvm.mvvm

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvm.Utils.Utilities
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.api.ApiUtilities
import com.example.retrofitmvvm.database.DatabaseDaoInterface
import com.example.retrofitmvvm.database.DatabaseMemeAbstract
import com.example.retrofitmvvm.meemmodel.Meme
import com.example.retrofitmvvm.meemmodel.MemeX
import java.sql.DatabaseMetaData

class MemesRepository(private val apiInterface: ApiInterface, private val databaseDaoInterface: DatabaseDaoInterface, private val context: Context) {

    suspend fun getData() : List<MemeX>{
        if (internetConnection(context)){
            val responce = apiInterface.getJokes()
            if (responce.isSuccessful){
                val meme = responce.body()
                try {
                    meme?.forEach {
                        databaseDaoInterface.insertMemeX(it)
                    }
                }catch (e : Exception){e.printStackTrace()}
                return meme!!
            }else{
                throw Exception(responce.message())
            }
        }else{
            val meme = databaseDaoInterface.getMemeFromDb()
            return meme.ifEmpty { throw Exception("No Internet") }
        }
    }

    private fun internetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    /*fun getAllDataFromDatabase() = databaseDaoInterface.getDataDao().getMemeFromDb()
    suspend fun insertInDatabase(memeX: List<MemeX>) = databaseDaoInterface.getDataDao().insertMemeX(memeX)
    fun getDataFromRetrofit(): ApiInterface = ApiUtilities.getIntence().create(ApiInterface::class.java)*/

    /*private val memeLiveData = MutableLiveData<Meme>()

    val meme : LiveData<Meme>
    get() = memeLiveData

    suspend fun getMemes() {
        val result = apiInterface.getJokes()
        if (result.body() != null) {
            memeLiveData.postValue(result.body())

             *//*if (Utilities.isNetworkAvailable(context)){
                instance.getDataDao().insertMemeX(result.body()!!.data.memes)
            }*//*
        }

        }*/
    }
