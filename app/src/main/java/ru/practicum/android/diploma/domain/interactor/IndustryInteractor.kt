package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.Industry

interface IndustryInteractor {
    suspend fun loadIndustries(): Result<List<Industry>>
}
