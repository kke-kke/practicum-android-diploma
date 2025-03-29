package ru.practicum.android.diploma.domain

data class FilterParameters(
    val areaId: Int?,
    val areaParentId: String,
    val areaName: String,
    val industryId: Int?,
    val industryName: String,
    val salary: Int?,
    val onlyWithSalary: Boolean
){
    companion object {
        val defaultFilters = FilterParameters(
            areaId = null,
            areaParentId = "",
            areaName = "",
            industryId = null,
            industryName = "",
            salary = null,
            onlyWithSalary = false
        )
    }
}
