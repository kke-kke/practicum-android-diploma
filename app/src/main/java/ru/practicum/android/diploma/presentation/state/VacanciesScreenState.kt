package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacanciesScreenState {
    data object Loading : VacanciesScreenState()
    data class Content(
        val vacancyList: List<Vacancy>,
        val foundVacanciesCount: Int,
        val isPaginationLoading: Boolean
    ) : VacanciesScreenState()

    data object NothingFound : VacanciesScreenState()
    data class NetworkError(val errorText: String) : VacanciesScreenState()
    data class ServerError(val errorText: String) : VacanciesScreenState()

}
