package ru.practicum.android.diploma.presentation.vacancydetails

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacancyDetailsScreenState {
    data object Loading : VacancyDetailsScreenState()
    data class Success(val vacancy: Vacancy) : VacancyDetailsScreenState()

    sealed class Error : VacancyDetailsScreenState() {
        data object NotFoundError : Error()
        data object NoInternetError : Error()
        data object OtherError : Error()
    }
}
