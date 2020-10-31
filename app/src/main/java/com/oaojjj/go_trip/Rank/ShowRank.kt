package com.oaojjj.go_trip.Rank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.oaojjj.go_trip.R
import kotlinx.android.synthetic.main.fragment_show_rink.view.*

class ShowRank : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_rink, container, false)
        setRecyclerView(view)
        return view
    }
    private fun setRecyclerView(view:View){
        view.rank_list.layoutManager=LinearLayoutManager(requireContext())
        view.rank_list.setHasFixedSize(true)
        view.rank_list.adapter=RankItemAdapter(makeRankList())
    }
    private fun makeRankList():ArrayList<RankItem>{
        val list= arrayListOf<RankItem>()
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,R.drawable.mountain))
        return list
    }
}