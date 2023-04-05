package org.diyorbek.mvvm_h1.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Src(
    val landscape: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String
): Parcelable