package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad

class VacancyDetailsInteractorImpl(private val vacancyDetailsRepository: VacancyDetailsRepository) : VacancyDetailsInteractor {
    override fun loadVacancyDetails(vacancyId: String): Flow<VacancyDetailsStateLoad> {
        return vacancyDetailsRepository.loadVacancy(vacancyId)
    }

//    override suspend fun deleteVacancy(vacancyId: String) {
//        vacancyDetailsRepository.deleteVacancy(vacancyId)
//    }
}
