package com.example.diploma.map.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diploma.util.Constants
import com.example.diploma.util.Constants.MAP_CROP_TABLE
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Entity(MAP_CROP_TABLE)
@Parcelize
data class MapCropPolygon(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val coordinates: List<LatLng>,
    val area: Double,
    val currentCulture: String?,
    val previousCulture: String?,
    val cropIndex: Double
):Parcelable
