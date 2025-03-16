package ru.practicum.android.diploma.data.dto.models

import com.google.gson.annotations.SerializedName

data class VacanciesResponse(
    @SerializedName("items") val items: List<VacancyDTO>,
    @SerializedName("found") val found: Int,
    @SerializedName("pages") val maxPages: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int
)
