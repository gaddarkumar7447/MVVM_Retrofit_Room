package com.example.retrofitmvvm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitmvvm.meemmodel.Meme

@Dao
interface DatabaseDaoInterface {

    @Insert
    suspend fun insertMemeX(dataMeme: List<Meme>)

    @Query("SELECT * FROM databaseDB")
    suspend fun getMemeFromDb() : List<Meme>
}