package com.example.retrofitmvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitmvvm.meemmodel.Meme
import com.example.retrofitmvvm.meemmodel.MemeX

@Dao
interface DatabaseDaoInterface {

    @Insert
    suspend fun insertMemeX(memeX: MemeX)

    @Query("SELECT * FROM databaseDB")
    fun getMemeFromDb() : List<MemeX>
}