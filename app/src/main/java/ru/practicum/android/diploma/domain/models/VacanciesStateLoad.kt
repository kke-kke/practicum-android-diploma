package ru.practicum.android.diploma.domain.models

data class VacanciesStateLoad(
    val vacanciesFound: VacanciesFound? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
