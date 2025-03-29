package ru.practicum.android.diploma.domain

data class FilterParameters(
    val areaId: String,
    val areaParentId: String,
    val areaName: String,
    val industryId: String,
    val industryName: String,
    val salary: Int,
    val currency: String,
    val onlyWithSalary: Boolean
){
    companion object {
        val defaultFilters = FilterParameters(
            areaId = "",
            areaParentId = "",
            areaName = "",
            industryId = "",
            industryName = "",
            salary = 0,
            currency = "RUR",
            onlyWithSalary = false
        )
    }
}
