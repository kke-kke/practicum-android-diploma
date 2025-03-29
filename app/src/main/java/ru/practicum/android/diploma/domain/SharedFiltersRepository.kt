package ru.practicum.android.diploma.domain

interface SharedFiltersRepository {
    suspend fun getCurrentFilters(): FilterParameters?
    suspend fun saveFilters(filters: FilterParameters)
    suspend fun updateFilters(updateBlock: (FilterParameters?) -> FilterParameters)
    suspend fun clearFilters()
}
