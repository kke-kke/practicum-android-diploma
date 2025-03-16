package ru.practicum.android.diploma.util

import com.google.gson.Gson

val gson = Gson()

inline fun <reified T> String.deserialize(): T? {
    return kotlin.runCatching { gson.fromJson(this, T::class.java) }.getOrNull()
}

fun Any.serialize(): String {
    return gson.toJson(this)
}
