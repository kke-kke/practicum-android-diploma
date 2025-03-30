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

    override fun saveAreaFilter(areaId: String?, areaParentId: String, areaName: String) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                areaId = areaId,
                areaParentId = areaParentId,
                areaName = areaName
            )
        }
    }

    override fun saveIndustryFilter(industryId: String?, industryName: String) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                industryId = industryId,
                industryName = industryName
            )
        }
    }

    override fun saveSalary(salary: Int?) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                salary = salary
            )
        }
    }

    override fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                onlyWithSalary = onlyWithSalary
            )
        }
    }

    override fun clearFilters() {
        filtersRepository.clearFilters()
    }

}
