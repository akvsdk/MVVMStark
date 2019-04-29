package com.zwl.jyq.mvvm_stark.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserInfo(
    var age: Int? = 0,
    var name: String? = "",
    var sex: Int? = 0
) : Parcelable