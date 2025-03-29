package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.AreaExtended

interface AreasInteractor {
    suspend fun loadAllAreas(): Result<List<AreaExtended>>
}
