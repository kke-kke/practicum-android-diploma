package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacanciesScreenState {
    data class Loading(val isPaginationLoading: Boolean) : VacanciesScreenState()
    data class Content(
        val vacancyList: List<Vacancy>,
        val foundVacanciesCount: Int,
        val isPaginationLoading: Boolean
    ) : VacanciesScreenState()

    data class NothingFound(val isPaginationLoading: Boolean) : VacanciesScreenState()
    data class NetworkError(val errorText: String, val isPaginationLoading: Boolean) : VacanciesScreenState()
    data class ServerError(val errorText: String, val isPaginationLoading: Boolean) : VacanciesScreenState()

}
