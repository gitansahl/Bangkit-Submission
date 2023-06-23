package com.gitan.mydicodingsubmission.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast (
    val seriesName: String,
    val realName: String,
    val birthDate: String,
    val description: String,
    val photo: String
    ): Parcelable