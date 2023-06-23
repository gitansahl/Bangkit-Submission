package com.gitan.mystoryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val photoUrl: String,
    val name: String,
    val description: String,
): Parcelable
