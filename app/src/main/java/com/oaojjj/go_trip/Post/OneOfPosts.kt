package com.oaojjj.go_trip.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oaojjj.go_trip.R

class OneOfPosts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_of_posts)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.holde_page,R.anim.down_page)
    }
}