package ru.practicum.android.diploma.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Salary

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

    @TypeConverter
    fun salaryToString(salary: Salary?): String {
        return (salary?.from ?: -1).toString() + "/" + (salary?.to ?: -1).toString() + "/" + salary?.currency
    }

    @TypeConverter
    fun stringToSalary(str: String): Salary {
        val arr = str.split("/")
        return Salary(arr[0].toInt(), arr[1].toInt(), arr[2])
    }

}
