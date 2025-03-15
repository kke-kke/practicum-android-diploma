package ru.practicum.android.diploma.data.dto.dto_models

data class VacancyDTO(
    val id: String,
    val name: String,
    val salary: SalaryDTO?,
    val employer: EmployerDTO,
    val snippet: SnippetDTO,
    val area: AreaDTO,
    val experience: ExperienceDTO,
    val schedule: ScheduleDTO,
    val published_at: String
)

data class SalaryDTO(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class EmployerDTO(
    val name: String,
    val logo_urls: LogoUrlsDTO?
)

data class LogoUrlsDTO(
    val original: String?
)

data class SnippetDTO(
    val requirement: String?,
    val responsibility: String?
)

data class AreaDTO(
    val name: String
)

data class ExperienceDTO(
    val name: String
)

data class ScheduleDTO(
    val name: String
)
