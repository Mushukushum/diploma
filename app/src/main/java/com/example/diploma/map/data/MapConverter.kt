package com.example.diploma.map.data

import android.util.Log
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class MapConverter {
    @TypeConverter
    fun fromLatLngList(latLngList: List<LatLng>): String {
        val sb = StringBuilder()
        for (latLng in latLngList) {
            sb.append(latLng.latitude)
            sb.append(",")
            sb.append(latLng.longitude)
            sb.append(";")
        }
        return sb.toString()
    }

    @TypeConverter
    fun toLatLngList(latLngListString: String): List<LatLng> {
        val latLngList = mutableListOf<LatLng>()
        val latLngArray = latLngListString.split(";").toTypedArray()
        for (latLngString in latLngArray) {
            if (latLngString.isNotEmpty()) {
                val latLngSplit = latLngString.split(",").toTypedArray()
                Log.d("latitude-longtitude" , latLngSplit[0].toString())
                val latitude = latLngSplit[0].toDouble()
                val longitude = latLngSplit[1].toDouble()
                latLngList.add(LatLng(latitude , longitude))
            }
        }
        return latLngList
    }
}