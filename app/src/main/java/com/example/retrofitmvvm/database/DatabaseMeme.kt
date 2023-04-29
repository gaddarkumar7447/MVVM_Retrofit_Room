package com.example.retrofitmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitmvvm.meemmodel.Meme

@Database(entities = [Meme::class], version = 1, exportSchema = false)
abstract class DatabaseMemeAbstract : RoomDatabase(){

    abstract fun getDataDao() : DatabaseDaoInterface

    companion object{
        private var INSTANCE : DatabaseMemeAbstract ?= null
        fun getDatabaseInstance(context: Context) : DatabaseMemeAbstract{
            synchronized(this){
                if (INSTANCE == null){
                    val  instance = Room.databaseBuilder(context, DatabaseMemeAbstract::class.java, "databaseDB").build()
                    INSTANCE = instance
                }
            }
            return INSTANCE!!
        }
        /*fun getDatabase(context: Context): DatabaseMemeAbstract {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseMemeAbstract::class.java,
                    "databaseDB.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }*/
    }
}