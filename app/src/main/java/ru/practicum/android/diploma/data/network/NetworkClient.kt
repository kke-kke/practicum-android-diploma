package ru.practicum.android.diploma.data.network

import retrofit2.Retrofit

class NetworkClient(private val retrofit: Retrofit) {

    fun getClient(): Retrofit {
        return retrofit
    }
}
