package com.oaojjj.go_trip.map.marker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oaojjj.go_trip.R

class MapPagerViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val pImg: ImageView = itemView.findViewById<ImageView>(R.id.imageView_map_cardview)
    val pContent: TextView = itemView.findViewById<TextView>(R.id.tv_map_cardview_pcontent)
}
