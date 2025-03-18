package ru.practicum.android.diploma.domain.models

data class VacanciesFound(
    val vacanciesList: List<Vacancy>,
    val found: Int,
    val maxPages: Int,
    val page: Int,
    val perPage: Int
)
