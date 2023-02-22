package com.example.retrofitmvvm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.retrofitmvvm.adapter.MemeAdapter
import com.example.retrofitmvvm.meemmodel.MemeX
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MemeAdapter
    private lateinit var memeX: MutableList<MemeX>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val url = "https://api.imgflip.com/get_memes"
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        memeX = mutableListOf()

        val stringRequest = StringRequest(Request.Method.GET, url, { responce ->

            val jsonObject = JSONObject(responce)
            val jsonArray = jsonObject.getJSONObject("data")
            val memes = jsonArray.getJSONArray("memes")


            for (i in 0 until memes.length()){
                val m = memes.getJSONObject(i)
                val name = m.getString("name")
                val id = m.getString("id")
                val url = m.getString("url")
                val width = m.getInt("width")
                val height = m.getInt("height")
                val box_count = m.getInt("box_count")
                val captions = m.getInt("captions")

                val memeList = MemeX(box_count,captions,height,id,name,url,width)
                memeX.add(memeList)
            }

        }, {

        })
        Volley.newRequestQueue(this).add(stringRequest)
        Log.d("Responce", "$memeX")

        adapter = MemeAdapter(this, memeX)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

    }
}