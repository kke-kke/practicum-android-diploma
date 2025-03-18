package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.models.AddressDTO
import ru.practicum.android.diploma.data.dto.models.AreaDTO
import ru.practicum.android.diploma.data.dto.models.EmployerDTO
import ru.practicum.android.diploma.data.dto.models.ExperienceDTO
import ru.practicum.android.diploma.data.dto.models.KeySkillDTO
import ru.practicum.android.diploma.data.dto.models.SalaryDTO
import ru.practicum.android.diploma.data.dto.models.ScheduleDTO
import ru.practicum.android.diploma.data.dto.models.VacancyDTO
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.KeySkill
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacanciesFound
import ru.practicum.android.diploma.domain.models.Vacancy

fun VacanciesResponse.toDomain(): VacanciesFound {
    return VacanciesFound(
        vacanciesList = vacanciesList.map { it.toDomain() },
        found = found,
        maxPages = maxPages,
        page = page,
        perPage = perPage
    )
}

fun VacancyDTO.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        vacancyUrl = vacancyUrl,
        salary = salary?.toDomain(),
        address = address?.toDomain(),
        employer = employer?.toDomain(),
        description = description,
        keySkills = keySkills?.map { it.toDomain() } ?: emptyList(),
        area = area.toDomain(),
        experience = experience?.toDomain(),
        schedule = schedule?.toDomain(),
        publishedAt = publishedAt,
        isFavorite = false

    )
}

fun SalaryDTO.toDomain(): Salary {
    return Salary(
        from = from ?: -1,
        to = to ?: -1,
        currency = currency ?: ""
    )
}

fun AddressDTO.toDomain(): Address {
    return Address(
        rawAddress = rawAddress ?: ""
    )
}

fun EmployerDTO.toDomain(): Employer {
    return Employer(
        name = name ?: "",
        logoUrl = logoUrls?.employerLogo ?: ""
    )
}

fun KeySkillDTO.toDomain(): KeySkill {
    return KeySkill(
        name = name ?: ""
    )
}

fun AreaDTO.toDomain(): Area {
    return Area(
        name = name ?: ""
    )
}

fun ExperienceDTO.toDomain(): Experience {
    return Experience(
        name = name ?: ""
    )
}

fun ScheduleDTO.toDomain(): Schedule {
    return Schedule(
        name = name ?: ""
    )
}

