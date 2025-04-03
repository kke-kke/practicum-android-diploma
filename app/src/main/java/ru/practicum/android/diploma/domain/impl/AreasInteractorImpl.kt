package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.interactor.AreasInteractor
import ru.practicum.android.diploma.domain.models.AreaExtended

class AreasInteractorImpl(
    private val areasRepository: AreasRepository
) : AreasInteractor {

    override suspend fun loadAllAreas(): Result<List<AreaExtended>> {
        return areasRepository.getAllAreas()
    }
}
