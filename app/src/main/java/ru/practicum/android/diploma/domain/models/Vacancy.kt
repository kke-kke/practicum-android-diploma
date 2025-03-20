package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val vacancyUrl: String,
    val salary: Salary?,
    val address: Address?,
    val employer: Employer?,
    val description: String?,
    val keySkills: List<KeySkill>,
    val area: Area,
    val experience: Experience?,
    val schedule: Schedule?,
    val publishedAt: String,
)

data class Salary(
    val from: Int,
    val to: Int,
    val currency: String
)

data class Address(
    val rawAddress: String
)

data class Employer(
    val name: String,
    val logoUrl: String
)

data class KeySkill(
    val name: String
)

data class Area(
    val name: String
)

data class Experience(
    val name: String
)

data class Schedule(
    val name: String
)
