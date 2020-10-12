package com.poly.minjin.poly_project.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Alcohol(
    var alcohol: String? = null,
    var amount: String? = null,
    var imageUrl: String? = null,
    var name: String? = null,
    var point: String? = null,
    var type: String? = null
) : Parcelable
