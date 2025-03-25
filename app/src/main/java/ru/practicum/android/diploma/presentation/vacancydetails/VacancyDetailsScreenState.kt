package ru.practicum.android.diploma.presentation.vacancydetails

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacancyDetailsScreenState {
    data object Loading : VacancyDetailsScreenState()
    data class Success(val vacancy: Vacancy) : VacancyDetailsScreenState()

    sealed class Error : VacancyDetailsScreenState() {
        data class NotFoundError(val errorMessage: String) : Error()
        data class NoInternetError(val errorMessage: String) : Error()
        data class OtherError(val errorMessage: String) : Error()
    }
}
