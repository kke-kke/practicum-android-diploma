package ru.practicum.android.diploma.domain.storage

import ru.practicum.android.diploma.domain.models.FilterParameters

interface SharedFiltersInteractor {
    fun getCurrentFilters(): FilterParameters
    fun saveAllFilters(filters: FilterParameters)
    fun clearFilters()
    fun getDraftFilters(): FilterParameters
    fun saveDraftFilters(filters: FilterParameters)
    fun clearDraftFilters()
}
