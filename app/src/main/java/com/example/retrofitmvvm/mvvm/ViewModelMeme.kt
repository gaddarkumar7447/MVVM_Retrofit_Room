package com.example.retrofitmvvm.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.meemmodel.Meme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelMeme(private val memesRepository : MemesRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            memesRepository.getMemes()
        }
    }

    val memes : LiveData<Meme>
    get() = memesRepository.meme

}