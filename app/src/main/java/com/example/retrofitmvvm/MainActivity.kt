package com.example.retrofitmvvm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.retrofitmvvm.adapter.MemeAdapter
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.api.ApiUtilities
import com.example.retrofitmvvm.database.DatabaseMemeAbstract
import com.example.retrofitmvvm.meemmodel.Data
import com.example.retrofitmvvm.meemmodel.MemeX
import com.example.retrofitmvvm.mvvm.MemesRepository
import com.example.retrofitmvvm.mvvm.ViewModelFactoryMeme
import com.example.retrofitmvvm.mvvm.ViewModelMeme
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    private lateinit var databaseMeme : DatabaseMemeAbstract
    private lateinit var adapter: MemeAdapter
    private lateinit var memeX: MutableList<MemeX>

    private lateinit var viewModelMeme: ViewModelMeme

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val user = MemeX(
            232,
            324,
            324,
            "56667894",
            "Gaddar kr Chaudhary",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYKvuVZxelNRL_p7CMM-S88rYlvHZlH6X8JoA7X7NQBg&s",
            323
        )
        memeX = mutableListOf<MemeX>(user)


        val apiInterface = ApiUtilities.getIntence().create(ApiInterface::class.java)


        try {
            databaseMeme = DatabaseMemeAbstract.getDatabase(applicationContext)
            MainScope().launch {
                databaseMeme.getDataDao().insertMemeX(memeX)
            }

            databaseMeme.getDataDao().getMemeFromDb().observe(this@MainActivity, Observer {
                Log.d("DataOdDataBase", "$it")
            })

        } catch (e: java.lang.Exception) {
            Toast.makeText(applicationContext, "Not creating instance", Toast.LENGTH_SHORT).show()
            Log.d("Instance", "$e")
        }

        // Call only via retrofit
        /*CoroutineScope(Dispatchers.Main).launch {
            val data = apiInterface.getJokes().body()?.data?.memes?.get(0)
            Log.d("Data1", "Data: $data")
        }*/
        /*val databaseMeme = DatabaseMeme.getDatabase(applicationContext)
        MainScope().launch {
            databaseMeme.getDataDao().insertMemeX(listOf(user))
        }*/

        // Call via mvvm pattern
        val memesRepository = MemesRepository(apiInterface, applicationContext)

        //viewModelMeme = ViewModelMeme(memesRepository)
        viewModelMeme = ViewModelProvider(this, ViewModelFactoryMeme(memesRepository))[ViewModelMeme::class.java]

        viewModelMeme.memes.observe(this, Observer {
            Log.d("Responce", "Res: ${it.data.memes[0]}")
            for (i in 0 until it.data.memes.size) {
                val value = it.data.memes[i]
                Log.d("Value: ", "$value")
                memeX.addAll(listOf(value))
            }
            adapter = MemeAdapter(this, memeX)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        })

        Log.d("Responce", "Before: $memeX")


        val url = "https://api.imgflip.com/get_memes"
        /*val stringRequest = StringRequest(Request.Method.GET, url, { responce ->

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
        Log.d("Responce", "After: $memeX")

        adapter = MemeAdapter(this, memeX)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter*/

    }
}