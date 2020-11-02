package com.oaojjj.go_trip.map.marker

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(lat: Double, lng: Double) : ClusterItem{
    private var mPosition: LatLng = LatLng(lat, lng)

    override fun getSnippet(): String? {
        return null
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getPosition(): LatLng {
        return mPosition
    }
}