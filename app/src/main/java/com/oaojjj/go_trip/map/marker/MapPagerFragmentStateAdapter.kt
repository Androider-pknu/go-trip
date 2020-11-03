package com.oaojjj.go_trip.map.marker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oaojjj.go_trip.R

class MapPagerFragmentStateAdapter(): RecyclerView.Adapter<MapPagerViewHolder>(){
    private var itemList =  ArrayList<CardViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapPagerViewHolder =
        MapPagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_map_cadview, parent, false))

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MapPagerViewHolder, position: Int) {
        holder.pImg.setImageResource(R.drawable.common_google_signin_btn_icon_dark)
        holder.pName.text = itemList[position].pName
        holder.pLocation.text = itemList[position].pLocation
    }

    fun addItem(item: CardViewModel){
        itemList.add(item)
    }
}

