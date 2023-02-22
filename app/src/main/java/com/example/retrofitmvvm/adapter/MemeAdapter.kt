package com.example.retrofitmvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.retrofitmvvm.R
import com.example.retrofitmvvm.meemmodel.MemeX

class MemeAdapter(private val context: Context, private val list: List<MemeX>) : RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        return MemeViewHolder(LayoutInflater.from(context).inflate(R.layout.linear, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val currentItem = list[position]
        holder.id.text = "ID: ${currentItem.id}"
        holder.ulr.text = "URL : ${currentItem.url}"
        holder.nameT.text = "NAME : ${currentItem.name}"
        holder.width1.text = "Width : ${currentItem.width}"
        holder.height.text = "Height : ${currentItem.height}"
        holder.cons.text = "Conse: ${currentItem.captions}"
        holder.box.text = "BOX : ${currentItem.box_count}"

        Glide.with(context).load(currentItem.url).into(holder.image)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MemeViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val id : TextView = view.findViewById(R.id.id)
        val ulr : TextView = view.findViewById(R.id.url)
        val nameT : TextView = view.findViewById(R.id.name)
        val width1 : TextView = view.findViewById(R.id.width)
        val height : TextView = view.findViewById(R.id.height)
        val cons : TextView = view.findViewById(R.id.captions)
        val box : TextView = view.findViewById(R.id.boxcount)
        val image : ImageView = view.findViewById(R.id.image)
    }
}