package com.project.newsnexus.data.model

import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val name: String,
    val url: String
): Parcelable

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(source: String): Source {
        return Gson().fromJson(source, Source::class.java)
    }
}