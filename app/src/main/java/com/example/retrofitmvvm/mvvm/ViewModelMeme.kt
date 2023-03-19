package com.example.retrofitmvvm.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.meemmodel.Meme
import com.example.retrofitmvvm.meemmodel.MemeX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelMeme(private val memesRepository : MemesRepository) : ViewModel() {

    private val memeLiveData1 = MutableLiveData<List<MemeX>>()
    val mutableLiveData : MutableLiveData<List<MemeX>> = memeLiveData1


    init {
        getData()
    }


    private fun getData(){
        viewModelScope.launch {
            //val result = memesRepository.getData()
            memeLiveData1.postValue(memesRepository.getData())
        }
    }

    /*fun getDataFromRetrofit() = viewModelScope.launch{
        if (memesRepository.getDataFromRetrofit().getJokes().isSuccessful){
            val result = memesRepository.getDataFromRetrofit().getJokes().body()
            result?.data?.memes?.let { memesRepository.insertInDatabase(it) }
            //memeLiveData.postValue(result)
        }
    }*/

    private fun internetConnection(): Boolean {
        return true
    }


    //suspend fun insertDataInto(memeX: List<MemeX>) = memesRepository.insertInDatabase(memeX)

    //fun getDataFromDatabase() = memesRepository.getAllDataFromDatabase()


    /*init {
        viewModelScope.launch(Dispatchers.IO) {
            memesRepository.getMemes()
        }
    }

    val memes : LiveData<Meme>
    get() = memesRepository.meme*/

}