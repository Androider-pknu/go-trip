package com.oaojjj.go_trip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.oaojjj.go_trip.Post.MakingPostFragment
import com.oaojjj.go_trip.Rank.RankingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        val testData:String="원질이 되는 것이다 그들은 앞이 긴지라 착목하는 곳이 원대하고 그들은 피가 더운지라 실현에 대한 자신과" +
                "용기가 있다 그러므로 그들은 이상의 보배를 능히 품으며 그들의이상은 아름답고 소듬스러운 열매를 맺어 우리 인생을 풍부하게."
    }
    private var postFragment: Fragment?=null
    private var rankFragment: Fragment?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_test_make_post.setOnClickListener {
            if(postFragment==null)
                postFragment=MakingPostFragment()
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, postFragment!!).addToBackStack(null).commit()
        }
        btn_test_rank.setOnClickListener {
            if(rankFragment==null)
                rankFragment=RankingFragment()
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, rankFragment!!).addToBackStack(null).commit()
        }
    }
}