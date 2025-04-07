package ru.practicum.android.diploma.domain.models

import java.io.Serializable

data class Industry(
    val id: String?,
    val name: String,
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
