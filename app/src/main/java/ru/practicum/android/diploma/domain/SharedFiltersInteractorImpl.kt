package ru.practicum.android.diploma.domain

class SharedFiltersInteractorImpl(
    private val filtersRepository: SharedFiltersRepository
) : SharedFiltersInteractor {
    override suspend fun getCurrentFilters(): FilterParameters {
        return filtersRepository.getCurrentFilters() ?: FilterParameters.defaultFilters
    }

    override suspend fun saveAreaFilter(areaId: Int?, areaParentId: String, areaName: String) {
        filtersRepository.updateFilters { current ->
            (current ?: FilterParameters.defaultFilters).copy(
                areaId = areaId,
                areaParentId = areaParentId,
                areaName = areaName
            )
        }
    }

    override suspend fun saveIndustryFilter(industryId: Int?, industryName: String) {
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
