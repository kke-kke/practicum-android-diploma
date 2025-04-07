package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesStateLoad

interface SearchVacanciesRepository {
    fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int,
        areaId: String?,
        industryId: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<VacanciesStateLoad>
}
