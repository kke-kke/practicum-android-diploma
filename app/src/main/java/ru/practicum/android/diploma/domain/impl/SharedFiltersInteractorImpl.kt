package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.storage.SharedFiltersInteractor
import ru.practicum.android.diploma.domain.storage.SharedFiltersRepository

class SharedFiltersInteractorImpl(
    private val filtersRepository: SharedFiltersRepository
) : SharedFiltersInteractor {
    override fun getCurrentFilters(): FilterParameters {
        return filtersRepository.getCurrentFilters() ?: FilterParameters.defaultFilters
    }

    override fun saveAllFilters(filters: FilterParameters) {
        filtersRepository.updateFilters {
            filters
        }
    }

    override fun clearFilters() {
        filtersRepository.clearFilters()
    }

}
