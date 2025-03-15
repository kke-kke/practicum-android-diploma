package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.domain.models.Response

inline fun <reified T> retrofit2.Response<T>.call(): Response<T> {
    val body = this.body() ?: return Response.Error(
        errorMessage = this.message(),
        errorCode = this.code()
    )

    return Response.Success(
        data = body,
        responseCode = this.code()
    )
}
