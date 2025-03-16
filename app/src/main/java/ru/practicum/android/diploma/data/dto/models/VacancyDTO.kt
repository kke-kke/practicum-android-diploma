package ru.practicum.android.diploma.data.dto.models

import com.google.gson.annotations.SerializedName

data class VacancyDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("salary") val salary: SalaryDTO? = null,
    @SerializedName("employer") val employer: EmployerDTO? = null,
    @SerializedName("snippet") val snippet: SnippetDTO? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("key_skills") val keySkills: List<KeySkillDTO>? = null,
    @SerializedName("area") val area: AreaDTO? = null,
    @SerializedName("experience") val experience: ExperienceDTO? = null,
    @SerializedName("schedule") val schedule: ScheduleDTO? = null,
    @SerializedName("published_at") val publishedAt: String? = null
)

data class SalaryDTO(
    @SerializedName("from") val from: Int? = null,
    @SerializedName("to") val to: Int? = null,
    @SerializedName("currency") val currency: String? = null
)

data class EmployerDTO(
    @SerializedName("name") val name: String? = null,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDTO? = null
)

data class LogoUrlsDTO(
    @SerializedName("original") val original: String? = null
)

data class SnippetDTO(
    @SerializedName("requirement") val requirement: String? = null,
    @SerializedName("responsibility") val responsibility: String? = null
)

data class KeySkillDTO(
    @SerializedName("name") val name: String? = null
)

data class AreaDTO(
    @SerializedName("name") val name: String? = null
)

data class ExperienceDTO(
    @SerializedName("name") val name: String? = null
)

data class ScheduleDTO(
    @SerializedName("name") val name: String? = null
)
