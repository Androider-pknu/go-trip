package com.oaojjj.go_trip.Post

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.oaojjj.go_trip.R
import kotlinx.android.synthetic.main.fragment_make_post.view.*


class MakingPostFragment : Fragment() {
    private val REQ_STORAGE_PERMISSION = 0
    private val REQ_GALLERY = 1
    private val FLAG_REQ_STORAGE = 102
    var rootView : View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_make_post, container, false)
        rootView = view
        setPictureButton(view)
        return view
    }
    private fun setPictureButton(view:View){
        view.btn_call_gallery.setOnClickListener {
            callGallery()
        }
    }
    private fun callGallery(){
        var writePermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE), REQ_STORAGE_PERMISSION)
        }
        else{
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            // MediaStore 은 안드로이드에서 외부 저장소를 관리하는 DB. 외부 저장소에 파일을 저장하기 위해서는
            // MediaStore 을 통해서만 가능.
            intent.type = "image/*"
            startActivityForResult(intent, REQ_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            val uri = data?.data
            rootView!!.test_img.setImageURI(uri)
        }
    }
}