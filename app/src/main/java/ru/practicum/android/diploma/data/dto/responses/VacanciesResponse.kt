package ru.practicum.android.diploma.data.dto.responses

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.models.VacancyDTO

data class VacanciesResponse(
    @SerializedName("items") val vacanciesList: List<VacancyDTO>,
    @SerializedName("found") val found: Int,
    @SerializedName("pages") val maxPages: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int
)
