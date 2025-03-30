package ru.practicum.android.diploma.domain.storage

import ru.practicum.android.diploma.domain.models.FilterParameters

interface SharedFiltersInteractor {
    fun getCurrentFilters(): FilterParameters
    fun saveAllFilters(filters: FilterParameters)
    fun saveAreaFilter(areaId: String?, areaParentId: String, areaName: String)
    fun saveIndustryFilter(industryId: String?, industryName: String)
    fun saveSalary(salary: Int?)
    fun setOnlyWithSalary(onlyWithSalary: Boolean)
    fun clearFilters()
}
