package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacanciesScreenState {
    data object Loading: VacanciesScreenState()
    data class Empty(val message: String): VacanciesScreenState()
    data class Content(val vacancyList: List<Vacancy>, val foundVacanciesCount: Int): VacanciesScreenState()

    sealed class Error(): VacanciesScreenState(){
        data class NoInternetError(val errorText: String): Error()
        data class ConnectionError(val errorText: String): Error()
    }
}
