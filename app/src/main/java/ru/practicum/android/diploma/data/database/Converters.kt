package ru.practicum.android.diploma.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

@ProvidedTypeConverter
class Converters(private val gson: Gson) {
    @TypeConverter
    fun listToJson(list: List<String>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToList(json: String?): List<String>? {
        return gson.fromJson(json, Array<String>::class.java)?.toList()
    }
}
