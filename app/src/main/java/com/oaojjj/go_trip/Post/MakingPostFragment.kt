package com.oaojjj.go_trip.Post

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oaojjj.go_trip.R
import kotlinx.android.synthetic.main.fragment_make_post.view.*


class MakingPostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_make_post, container, false)
        setPictureButton(view)
        return view
    }
    private fun setPictureButton(view:View){
        view.btn_call_gallery.setOnClickListener {
            startActivity(Intent(requireContext(),SelectPhoto::class.java))
            requireActivity().overridePendingTransition(R.anim.up_page,R.anim.holde_page)
        }
    }
}