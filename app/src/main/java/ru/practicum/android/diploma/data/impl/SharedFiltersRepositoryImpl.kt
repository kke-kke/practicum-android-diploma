package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.storage.FiltersStorage
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.storage.SharedFiltersRepository

class SharedFiltersRepositoryImpl(
    private val filtersStorage: FiltersStorage
) : SharedFiltersRepository {
    override suspend fun getCurrentFilters(): FilterParameters? {
        return withContext(Dispatchers.IO) {
            filtersStorage.getFilters()
        }
    }

    override suspend fun saveFilters(filters: FilterParameters) {
        withContext(Dispatchers.IO) {
            filtersStorage.putFilters(filters)
        }
    }

    override suspend fun updateFilters(updateBlock: (FilterParameters?) -> FilterParameters) {
        withContext(Dispatchers.IO) {
            val current = filtersStorage.getFilters()
            val updated = updateBlock(current)
            filtersStorage.putFilters(updated)
            updated
        }
    }

    override suspend fun clearFilters() {
        withContext(Dispatchers.IO) {
            filtersStorage.clear()
        }
    }

}
