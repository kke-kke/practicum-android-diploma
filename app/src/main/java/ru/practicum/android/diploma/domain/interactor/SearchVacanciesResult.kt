package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.VacanciesFound

sealed interface SearchVacanciesResult {
    data class Success(val vacanciesFound: VacanciesFound) : SearchVacanciesResult
    data object NothingFound : SearchVacanciesResult
    data class NetworkError(val text: String) : SearchVacanciesResult
    data class ServerError(val text: String) : SearchVacanciesResult
    data object Loading : SearchVacanciesResult
}
