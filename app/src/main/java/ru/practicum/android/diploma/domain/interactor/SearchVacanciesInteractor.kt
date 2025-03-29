package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow

interface SearchVacanciesInteractor {
    fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int,
        areaId: Int?,
        industryId: Int?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<SearchVacanciesResult>?
}
