package ru.practicum.android.diploma.domain.models

data class FilterParameters(
    val areaId: String?,
    val areaParentId: String,
    val areaName: String,
    val areaParentName: String,
    val industryId: String?,
    val industryName: String,
    val salary: Int?,
    val onlyWithSalary: Boolean
) {
    companion object {
        val defaultFilters = FilterParameters(
            areaId = null,
            areaParentId = "",
            areaName = "",
            areaParentName = "",
            industryId = null,
            industryName = "",
            salary = null,
            onlyWithSalary = false
        )
    }
}
