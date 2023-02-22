package com.example.retrofitmvvm.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class ViewModelFactoryMeme(private val memesRepository: MemesRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelMeme(memesRepository) as T
    }
}