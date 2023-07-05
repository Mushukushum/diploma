package com.example.diploma.dayduty.data

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

class Converter {

    @TypeConverter
    fun fromLocalDate(date:LocalDate): String{
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(date: String): LocalDate{
        return LocalDate.parse(date)
    }


}