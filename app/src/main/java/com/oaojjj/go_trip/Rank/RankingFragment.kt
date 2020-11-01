package com.oaojjj.go_trip.Rank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.oaojjj.go_trip.MainActivity
import com.oaojjj.go_trip.Post.OneOfPosts
import com.oaojjj.go_trip.R
import kotlinx.android.synthetic.main.fragment_show_rink.view.*

class RankingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_rink, container, false)
        setRecyclerView(view)
        return view
    }
    private fun setRecyclerView(view:View){
        view.rank_list.layoutManager=LinearLayoutManager(requireContext())
        view.rank_list.setHasFixedSize(true)
        val rankAdapter=RankItemAdapter(makeRankList())
        rankAdapter.setRankClickListener(object:RankItemAdapter.OnRankClickListener{
            override fun onClick(view:View, position:Int){
                startActivity(Intent(requireContext(),OneOfPosts()::class.java))
                requireActivity().overridePendingTransition(R.anim.up_page,R.anim.holde_page)
            }
        })
        view.rank_list.adapter=rankAdapter

    }
    private fun makeRankList():ArrayList<RankItem>{
        val list= arrayListOf<RankItem>()
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        list.add(RankItem(R.drawable.no_profile_img, "ㅋㅋㅋㅋ",13,MainActivity.testData,R.drawable.mountain))
        return list
    }
}