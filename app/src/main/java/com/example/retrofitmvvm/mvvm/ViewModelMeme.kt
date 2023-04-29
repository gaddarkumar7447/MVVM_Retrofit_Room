package com.example.retrofitmvvm.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.Utils.ApiResponce
import com.example.retrofitmvvm.database.DatabaseDaoInterface
import com.example.retrofitmvvm.database.DatabaseMemeAbstract
import com.example.retrofitmvvm.meemmodel.Meme
import com.example.retrofitmvvm.meemmodel.MemeData
import com.example.retrofitmvvm.meemmodel.MemeResponce
import kotlinx.coroutines.launch

class ViewModelMeme(private val memesRepository : MemesRepository, private val databaseMemeAbstract: DatabaseDaoInterface) : ViewModel() {
    val memeLiveDataMeme: LiveData<ApiResponce<MemeResponce>> = memesRepository.memeLiveDataMeme

    fun getMeme(){
        viewModelScope.launch {
            memesRepository.getMeme()
        }
    }

    var dataFromDataBaseMutable = MutableLiveData<List<Meme>>()

    fun getDataBase(){
        viewModelScope.launch {
            dataFromDataBaseMutable.postValue(databaseMemeAbstract.getMemeFromDb())
        }
    }
}