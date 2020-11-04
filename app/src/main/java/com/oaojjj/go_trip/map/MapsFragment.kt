package com.oaojjj.go_trip.map

import android.Manifest
import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.fragment_maps.*


class MapsFragment : Fragment(){
    private val PERM_FLAG = 99
    private val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback     // 위치 콜백
    private var initMap = true

    private lateinit var mClusterManager: ClusterManager<MarkerMyItem>
    private lateinit var mLocation: LatLng
    private lateinit var cardViewAdapter: MapPagerFragmentStateAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isPermitted()){
            startProcess()
            vp_map_cardview.adapter = cardViewAdapter
            // 좌/우 노출되는 크기를 크게하려면 offsetPx 증가
            val offsetPx = 30.dpToPx(resources.displayMetrics)
            vp_map_cardview.setPadding(offsetPx, offsetPx, offsetPx, offsetPx)

            // 페이지간 마진 크게하려면 pageMarginPx 증가
            val pageMarginPx = 5.dpToPx(resources.displayMetrics)
            val marginTransformer = MarginPageTransformer(pageMarginPx)
            vp_map_cardview.setPageTransformer(marginTransformer)

            vp_map_cardview.offscreenPageLimit = 2
        }
        else{   // 권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(requireActivity(),permissions, PERM_FLAG)
        }
    }

    private fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

    @SuppressLint("MissingPermission")
    // Callback
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
        mClusterManager = ClusterManager(requireContext(), mMap)
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)

        setUpdateLocationListener()
        markerClickListener()
        addItem()
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

    private fun addCardView(markerItem: MarkerMyItem){

    }

    private fun addItem(){  //  임시 마커 추가
        var lat = 37.2000
        var lng = 127.103021930129409

        for (i in 1..10){
            val offset = i/ 60.toDouble()
            lat += offset
            lng += offset
            val offsetItem = MarkerMyItem(lat, lng)
            mClusterManager.addItem(offsetItem)
        }
    }

    private fun setCardViewList(){      // 임시 카드뷰 데이터
        cardViewAdapter = MapPagerFragmentStateAdapter()
        cardViewAdapter.addItem(
            CardViewModel(R.drawable.koreanfood_basic, "이학진", "경남 창원시 성산구")
        )
        cardViewAdapter.addItem(
            CardViewModel(R.drawable.common_google_signin_btn_icon_light, "임종윤", "울산 북구 어디서...")
        )
        cardViewAdapter.addItem(
            CardViewModel(R.drawable.koreanfood_basic, "김영민", "김해 북구 어디서...")
        )
        cardViewAdapter.notifyDataSetChanged()
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
                        Log.d("로케이션", "$i ${location.latitude}, ${location.longitude}")
                        //setLastLocation(location)
                        if(initMap) {
                            initMap = false
                            initLocation(location)
                        }
                        updateLocation(location)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun updateLocation(location: Location){

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
        setCardViewList()   // cardView Set
        map_cursor.visibility = View.VISIBLE
        vp_map_cardview.visibility = View.VISIBLE
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
}