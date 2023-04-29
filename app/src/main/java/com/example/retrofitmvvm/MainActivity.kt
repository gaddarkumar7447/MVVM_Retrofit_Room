package com.example.retrofitmvvm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitmvvm.Utils.ApiResponce
import com.example.retrofitmvvm.Utils.Utilities
import com.example.retrofitmvvm.Utils.Utilities.isNetworkAvailable
import com.example.retrofitmvvm.adapter.MemeAdapter
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.api.ApiUtilities
import com.example.retrofitmvvm.database.DatabaseDaoInterface
import com.example.retrofitmvvm.database.DatabaseMemeAbstract
import com.example.retrofitmvvm.meemmodel.Meme
import com.example.retrofitmvvm.meemmodel.MemeResponce
import com.example.retrofitmvvm.mvvm.MemesRepository
import com.example.retrofitmvvm.mvvm.ViewModelFactoryMeme
import com.example.retrofitmvvm.mvvm.ViewModelMeme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MemeAdapter
    private lateinit var meme: List<Meme>
    private lateinit var viewModelMeme: ViewModelMeme

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)

        meme = listOf()
        adapter = MemeAdapter(this, meme)

        val apiInterface = ApiUtilities.getIntence().create(ApiInterface::class.java)
        val databaseDaoInterface: DatabaseDaoInterface = DatabaseMemeAbstract.getDatabaseInstance(this).getDataDao()
        val memesRepository = MemesRepository(apiInterface, databaseDaoInterface)

        viewModelMeme = ViewModelProvider(this, ViewModelFactoryMeme(memesRepository, databaseDaoInterface))[ViewModelMeme::class.java]

        viewModelMeme.getDataBase()
        viewModelMeme.dataFromDataBaseMutable.observe(this, Observer {
            it.forEach {
                Log.d("MEME", it.toString())
            }
        })

        viewModelMeme.getMeme()
        if (isNetworkAvailable(this)){
            fetchDataFromApi(progressBar, recyclerView)
        }else{
            MainScope().launch {
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = MemeAdapter(this@MainActivity, databaseDaoInterface.getMemeFromDb())
                recyclerView.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchDataFromApi(progressBar: ProgressBar, recyclerView: RecyclerView, ) {
        viewModelMeme.memeLiveDataMeme.observe(this, Observer {
            when (it) {
                is ApiResponce.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is ApiResponce.Successful -> {
                    //meme = it.data?.data?.memes!!
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = MemeAdapter(this, it.data?.data?.memes!!)
                    recyclerView.setHasFixedSize(true)
                    adapter.notifyDataSetChanged()
                    progressBar.visibility = View.INVISIBLE
                    //Log.d("MEME", it.data.data.memes.toString())

                }
                is ApiResponce.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.d("MEME", it.message.toString())
                    progressBar.visibility = View.INVISIBLE

                }
            }
        })
    }
}






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