package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaExtended

interface AreasRepository {
    suspend fun getAllAreas(): Result<List<AreaExtended>>
}
