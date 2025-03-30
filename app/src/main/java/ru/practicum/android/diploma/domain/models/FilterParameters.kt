package ru.practicum.android.diploma.domain.models

data class FilterParameters(
    val salaryFrom: Int?,
    val excludeNoSalary: Boolean,
    val industryId: String?,
    val industryName: String?,

    val countryId: String?,
    val countryName: String?,
    val regionId: String?,
    val regionName: String?,
)
