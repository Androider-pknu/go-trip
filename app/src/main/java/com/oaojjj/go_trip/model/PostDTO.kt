package com.oaojjj.go_trip.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class PostDTO(
    val post_id: Int,
    val author_id: Int,
    val content: String,
    val create_by: String,
    val latitude: Double,
    val longitude: Double,
    var image_path: String

) : Parcelable