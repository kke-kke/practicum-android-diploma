package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.data.storage.FiltersStorage
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.storage.SharedFiltersRepository

class SharedFiltersRepositoryImpl(
    private val filtersStorage: FiltersStorage
) : SharedFiltersRepository {
    override fun getCurrentFilters(): FilterParameters? {
        return filtersStorage.getFilters()

    }

    override fun saveFilters(filters: FilterParameters) {
        filtersStorage.putFilters(filters)
    }

    override fun updateFilters(updateBlock: (FilterParameters?) -> FilterParameters) {
        val current = filtersStorage.getFilters()
        val updated = updateBlock(current)
        filtersStorage.putFilters(updated)
    }

    override fun clearFilters() {
        filtersStorage.clear()
    }
    override fun getDraftFilters(): FilterParameters? {
        return filtersStorage.getDraftFilters()
    }

    override fun saveDraftFilters(filters: FilterParameters) {
        filtersStorage.putDraftFilters(filters)
    }

    override fun clearDraftFilters() {
        filtersStorage.clearDraftFilters()
    }
}
