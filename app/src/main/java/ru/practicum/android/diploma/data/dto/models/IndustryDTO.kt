package ru.practicum.android.diploma.data.dto.models

import com.google.gson.annotations.SerializedName

data class IndustryDTO(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("industries") val industries: List<IndustryDTO>? = null
)
