package ru.practicum.android.diploma.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToList(json: String?): List<String>? {
        return Gson().fromJson(json, Array<String>::class.java)?.toList()
    }
}
