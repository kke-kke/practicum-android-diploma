package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad

interface VacancyDetailsRepository {
    fun loadVacancy(vacancyId: String): Flow<VacancyDetailsStateLoad>
//    suspend fun deleteVacancy(vacancyId: String)
}
