package com.oaojjj.go_trip.map.marker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oaojjj.go_trip.R

class MapPagerFragmentStateAdapter(private val con: Context): RecyclerView.Adapter<MapPagerViewHolder>(){
    private var itemList =  ArrayList<CardViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapPagerViewHolder =
        MapPagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_map_cadview, parent, false))

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun removeItem(){
        itemList.clear()
    }

    override fun onBindViewHolder(holder: MapPagerViewHolder, position: Int) {
        holder.pContent.text = itemList[position].pContent

        if(itemList[position].img_url == null){     // IMG_URL 이 NULL 이면 기본 이미지
            holder.pImg.setImageResource(R.drawable.koreanfood_basic)   // basic Img
        }
        else{
            Glide.with(con).load(itemList[position].img_url).into(holder.pImg)
        }

        // cardVIEW EVENT LISTENER
        holder.itemView.setOnClickListener {

        }
    }

    fun addItem(item: CardViewModel){
        itemList.add(item)
    }
}

