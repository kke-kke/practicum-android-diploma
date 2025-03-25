package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad

interface VacancyDetailsInteractor {
    fun loadVacancyDetails(vacancyId: String): Flow<VacancyDetailsStateLoad>
}
