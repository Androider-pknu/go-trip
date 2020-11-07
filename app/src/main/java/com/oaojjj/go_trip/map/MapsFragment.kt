package com.oaojjj.go_trip.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.oaojjj.go_trip.R
import com.oaojjj.go_trip.map.marker.CardViewModel
import com.oaojjj.go_trip.map.marker.MapPagerFragmentStateAdapter
import com.oaojjj.go_trip.map.marker.MarkerMyItem
import com.oaojjj.go_trip.model.PostDTO
import com.oaojjj.go_trip.util.AWSRetrofit
import kotlinx.android.synthetic.main.fragment_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsFragment : Fragment(), GoogleMap.OnMapClickListener{
    private val PERM_FLAG = 99
    private val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback     // 위치 콜백
    private var initMap = true

    private lateinit var mClusterManager: ClusterManager<MarkerMyItem>
    private lateinit var mLocation: LatLng
    private lateinit var cardViewAdapter:  MapPagerFragmentStateAdapter
    private var userPostList = ArrayList<PostDTO>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cardViewAdapter = MapPagerFragmentStateAdapter(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isPermitted()){  // 권한이 승인되면 MAP 보여줌
            startProcess()
            vp_map_cardview.adapter = cardViewAdapter

            // cardview design 설정
            val offsetPx = 30.dpToPx(resources.displayMetrics)   // 좌/우 노출되는 크기를 크게하려면 offsetPx 증가
            vp_map_cardview.setPadding(offsetPx, offsetPx, offsetPx, offsetPx)

            val pageMarginPx = 5.dpToPx(resources.displayMetrics)   // 페이지간 마진 크게하려면 pageMarginPx 증가
            val marginTransformer = MarginPageTransformer(pageMarginPx)
            vp_map_cardview.setPageTransformer(marginTransformer)
            vp_map_cardview.offscreenPageLimit = 2
        }
        else{   // 권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(requireActivity(),permissions, PERM_FLAG)
        }
    }

    private fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

    private fun getPostList(){  // Marker 에 찍을 Post List 가져옴
        val userId = 2  // 임시로 userID SET
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getPostList(userId)

        call.enqueue(object : Callback<List<PostDTO>> {
            override fun onFailure(call: Call<List<PostDTO>>, t: Throwable) {
                Log.d("MapGetPost", "GET POST FAI")
                Log.d("MapGetPost", t.message.toString())
            }

            override fun onResponse(call: Call<List<PostDTO>>, response: Response<List<PostDTO>>) {
                if(response.isSuccessful){
                    Log.d("MapGetPostSuccess", "GET POST SUCCESS\n, ${response.body()}")
                    addItem(response.body() as ArrayList<PostDTO>)
                }
            }
        })
    }

    @SuppressLint("MissingPermission")
    // Callback
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.setOnMapClickListener(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
        mClusterManager = ClusterManager(requireContext(), mMap)    // 클러스터 매니저 등록
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)

        setUpdateLocationListener()     // 내위치 자동업데이트
        markerClickListener()           // 마커 클릭 리스너
        getPostList()   // PostList 받음
    }

    // 마커 클릭 리스너
    private fun markerClickListener(){
        mClusterManager.setOnClusterItemClickListener {
            mMap.moveCamera(CameraUpdateFactory.newLatLng
                (LatLng(it.position.latitude, it.position.longitude)))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
            addCardView(it)
            return@setOnClusterItemClickListener true
        }
    }

    private fun addCardView(markerItem: MarkerMyItem){      // 찍은 마커에 대한 정보를 가지고 카드뷰 data set
        cardViewAdapter.removeItem()
        vp_map_cardview.visibility = View.VISIBLE
        for (postItem in userPostList){
            // marker 의 위치를 이용해 cardView data set
            if(postItem.latitude == markerItem.position.latitude &&
                    postItem.longitude == markerItem.position.longitude){
                cardViewAdapter.addItem(CardViewModel(postItem.image_path, postItem.content))
            }
        }
        cardViewAdapter.notifyDataSetChanged()
    }

  private fun addItem(postList: ArrayList<PostDTO>){  //  마커 추가
      userPostList = postList  // userPostList 에게 postList 대입입
      for(location in postList){
          val lat = location.latitude
          val lng = location.longitude
          val markerItem = MarkerMyItem(lat, lng)
          mClusterManager.addItem(markerItem)   // ClusterManager 에게 marker 추가
      }
  }

    // 내위치를 가져옴
    @SuppressLint("MissingPermission")
    fun setUpdateLocationListener(){
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 100    // 0.1 second
        }
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for((i, location) in it.locations.withIndex()){
                        if(initMap) {
                            initMap = false
                            initLocation(location)
                        }
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun initLocation(location: Location){
        val myLocation = LatLng(location.latitude, location.longitude)
        mLocation = myLocation
        val cameraOption = CameraPosition.Builder()
            .target(myLocation)
            .zoom(15.0f)
            .build()

        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.moveCamera(camera)
    }

    private fun isPermitted(): Boolean{     // 권한 체크
        for(perm in permissions){
            if((ContextCompat.checkSelfPermission(requireContext(), perm)!= PERMISSION_GRANTED)){
                return false
            }
        }
        return true
    }

    private fun startProcess(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        map_cursor.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERM_FLAG -> {
                var check = true
                for (grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        check = false
                        break
                    }
                }
                if(check){
                    startProcess()
                } else{
                    Toast.makeText(requireContext(), "권한을 승인하세요", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onMapClick(p0: LatLng?) {
        Log.d("Map Click", "map이 눌려짐")
        vp_map_cardview.visibility = View.GONE
    }
}