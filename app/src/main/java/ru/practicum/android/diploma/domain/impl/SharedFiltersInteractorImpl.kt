package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.storage.SharedFiltersInteractor
import ru.practicum.android.diploma.domain.storage.SharedFiltersRepository

class SharedFiltersInteractorImpl(
    private val filtersRepository: SharedFiltersRepository
) : SharedFiltersInteractor {
    override suspend fun getCurrentFilters(): FilterParameters {
        return filtersRepository.getCurrentFilters() ?: FilterParameters.defaultFilters
    }

    override suspend fun saveAllFilters(filters: FilterParameters) {
        filtersRepository.updateFilters {
            filters
        }
    }

    override suspend fun saveAreaFilter(areaId: String?, areaParentId: String, areaName: String) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                areaId = areaId,
                areaParentId = areaParentId,
                areaName = areaName
            )
        }
    }

    override suspend fun saveIndustryFilter(industryId: String?, industryName: String) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                industryId = industryId,
                industryName = industryName
            )
        }
    }

    override suspend fun saveSalary(salary: Int?) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                salary = salary
            )
        }
    }

    override suspend fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                onlyWithSalary = onlyWithSalary
            )
        }
    }

    override suspend fun clearFilters() {
        filtersRepository.clearFilters()
    }

}
