package com.example.retrofitmvvm.meemmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity("databaseDB")
data class Meme(

    @SerializedName("box_count")
    val box_count: Int,
    val captions: Int,
    val height: Int,

    @PrimaryKey(false)
    val id: String,

    val name: String,
    val url: String,
    val width: Int
)