package com.oaojjj.go_trip.Post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oaojjj.go_trip.R
import kotlinx.android.synthetic.main.fragment_make_post.view.*


class MakingPostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_make_post, container, false)
        return view
    }
}