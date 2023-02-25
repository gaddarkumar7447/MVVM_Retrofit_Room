package com.example.retrofitmvvm.meemmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

//memes

@Entity(tableName = "databaseDB")
data class MemeX(
    val box_count: Int,
    val captions: Int,
    val height: Int,
    @PrimaryKey val id: String,
    val name: String,
    val url: String,
    val width: Int
)