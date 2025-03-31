package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow

interface SearchVacanciesInteractor {
    fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int,
        areaId: String?,
        industryId: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<SearchVacanciesResult>?
}
