package com.oaojjj.go_trip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oaojjj.go_trip.Post.MakePost
import com.oaojjj.go_trip.Rank.ShowRank
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_test_make_post.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame,
                MakePost()
            ).commit()
        }
        btn_test_rank.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, ShowRank()).commit()
        }
    }
}