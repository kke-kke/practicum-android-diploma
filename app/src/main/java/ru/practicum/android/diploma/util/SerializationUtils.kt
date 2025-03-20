package ru.practicum.android.diploma.util

import com.google.gson.Gson

inline fun <reified T> String.deserialize(gson: Gson): T? {
    return kotlin.runCatching { gson.fromJson(this, T::class.java) }.getOrNull()
}

fun Any.serialize(gson: Gson): String {
    return gson.toJson(this)
}
