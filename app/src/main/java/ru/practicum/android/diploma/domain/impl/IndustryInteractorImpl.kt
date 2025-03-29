package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.interactor.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Industry

class IndustryInteractorImpl(
    private val industryRepository: IndustryRepository
) : IndustryInteractor {

    override suspend fun loadIndustries(): Result<List<Industry>> {
        return industryRepository.getIndustries()
    }
}
