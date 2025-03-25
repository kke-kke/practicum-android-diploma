package ru.practicum.android.diploma.domain.models

data class VacancyDetailsStateLoad(
    val vacancy: Vacancy? = null,
    val isNotFoundError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isOtherError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
