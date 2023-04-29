package com.example.retrofitmvvm.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvm.database.DatabaseDaoInterface

class ViewModelFactoryMeme(private val memesRepository: MemesRepository, private val daoInterface: DatabaseDaoInterface) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelMeme(memesRepository, daoInterface) as T
    }
}