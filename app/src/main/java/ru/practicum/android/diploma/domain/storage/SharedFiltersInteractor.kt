package ru.practicum.android.diploma.domain.storage

import ru.practicum.android.diploma.domain.models.FilterParameters

interface SharedFiltersInteractor {
    suspend fun getCurrentFilters(): FilterParameters
    suspend fun saveAllFilters(filters: FilterParameters)
    suspend fun saveAreaFilter(areaId: String?, areaParentId: String, areaName: String)
    suspend fun saveIndustryFilter(industryId: String?, industryName: String)
    suspend fun saveSalary(salary: Int?)
    suspend fun setOnlyWithSalary(onlyWithSalary: Boolean)
    suspend fun clearFilters()
}
