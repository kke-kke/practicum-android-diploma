package ru.practicum.android.diploma.domain.models

data class VacanciesStateLoad(
    val vacanciesFound: VacanciesFound? = null,
    val isNetworkError: Boolean = false,
    val isServerError: Boolean = false,
    val isLoading: Boolean = false,
)
