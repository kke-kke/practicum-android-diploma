package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.VacanciesFound

sealed interface SearchVacanciesResult {
    data class Success(val vacanciesFound: VacanciesFound) : SearchVacanciesResult
    data class Error(
        val isNothingFound: Boolean = false,
        val isNetworkError: Boolean = false,
        val isNoInternet:   Boolean = false
    ) : SearchVacanciesResult

    data object Loading : SearchVacanciesResult
}
