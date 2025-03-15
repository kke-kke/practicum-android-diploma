package ru.practicum.android.diploma.data.dto.dto_models

data class VacanciesResponse(
    val items: List<VacancyDTO>,
    val found: Int,
    val pages: Int,
    val page: Int,
    val per_page: Int
)
