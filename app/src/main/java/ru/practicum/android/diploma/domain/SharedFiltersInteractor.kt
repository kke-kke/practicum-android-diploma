package ru.practicum.android.diploma.domain

interface SharedFiltersInteractor {
    suspend fun getCurrentFilters(): FilterParameters
    suspend fun saveAreaFilter(areaId: Int?, areaParentId: String, areaName: String)
    suspend fun saveIndustryFilter(industryId: Int?, industryName: String)
    suspend fun saveSalary(salary: Int?)
    suspend fun setOnlyWithSalary(onlyWithSalary: Boolean)
    suspend fun clearFilters()
}
