package ru.practicum.android.diploma.domain

interface SharedFiltersInteractor {
    suspend fun getCurrentFilters(): FilterParameters
    suspend fun saveAreaFilter(areaId: String, areaParentId: String, areaName: String)
    suspend fun saveIndustryFilter(industryId: String, industryName: String)
    suspend fun saveSalary(salary: Int, currency: String)
    suspend fun setOnlyWithSalary(onlyWithSalary: Boolean)
    suspend fun clearFilters()
}
