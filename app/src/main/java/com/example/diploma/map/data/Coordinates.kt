package com.example.diploma.map.data

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
@Parcelize class Coordinates(var coordinates: List<LatLng>):Parcelable