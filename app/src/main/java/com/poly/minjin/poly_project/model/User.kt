package com.poly.minjin.poly_project.model

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class User(
    var id : String? = null,
    var pw : String? = null,
    var nickName : String? = null

) : Parcelable