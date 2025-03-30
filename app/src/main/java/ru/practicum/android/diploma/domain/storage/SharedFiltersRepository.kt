package ru.practicum.android.diploma.domain.storage

import ru.practicum.android.diploma.domain.models.FilterParameters

interface SharedFiltersRepository {
    fun getCurrentFilters(): FilterParameters?
    fun saveFilters(filters: FilterParameters)
    fun updateFilters(updateBlock: (FilterParameters?) -> FilterParameters)
    fun clearFilters()
}
