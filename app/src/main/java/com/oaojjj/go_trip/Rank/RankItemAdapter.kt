package com.oaojjj.go_trip.Rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.oaojjj.go_trip.R

class RankItemAdapter(private val list:ArrayList<RankItem>) : RecyclerView.Adapter<RankItemAdapter.CustomViewHolder>(){

    interface OnRankClickListener{
        fun onClick(view: View, position: Int)
    }
    private lateinit var rankClickListener: OnRankClickListener
    fun setRankClickListener(rankClickListener: OnRankClickListener){
        this.rankClickListener=rankClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.rank_items,parent,false)
        return CustomViewHolder(
            view
        )
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rankProfileImg.setImageResource(list.get(position).profile)
        holder.rankkNickName.text=list.get(position).nickName
        holder.numOfVistiedLocation.text=list.get(position).numberOfVisitedLocation.toString()
        holder.rankPostText.text=list.get(position).postText
        holder.rankPostImg.setImageResource(list.get(position).postImg)
        holder.rankLayout.setOnClickListener{
            rankClickListener.onClick(it,position)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rankProfileImg=itemView.findViewById<ImageView>(R.id.rank_profile_img)
        val rankkNickName=itemView.findViewById<TextView>(R.id.rank_nickname)
        val numOfVistiedLocation=itemView.findViewById<TextView>(R.id.rank_num_visited_location)
        val rankPostText=itemView.findViewById<TextView>(R.id.rank_post_text)
        val rankPostImg = itemView.findViewById<ImageView>(R.id.rank_post_img)
        val rankLayout=itemView.findViewById<ConstraintLayout>(R.id.rank_layout)
    }
}