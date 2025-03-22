package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesStateLoad

interface SearchVacanciesRepository {
    fun searchVacancies(queryMap: Map<String, String>): Flow<VacanciesStateLoad>
}
