package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.database.entities.VacancyEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.KeySkill
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.VacancyUtils

fun VacancyEntity.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        vacancyUrl = alternateUrl ?: "",
        salary = salary,
        address = null,
        employer = Employer(
            name = companyName ?: "",
            logoUrl = companyIcon ?: ""
        ),
        description = description,
        keySkills = keySkills.map { KeySkill(it) },
        area = Area(name = departmentName),
        experience = experience?.let { Experience(it) },
        schedule = workFormat?.let { Schedule(it) },
        employment = employment?.let { Employment(it) },
        publishedAt = "",
    )
}

fun Vacancy.toEntity(): VacancyEntity {
    return VacancyEntity(
        id = id,
        name = name,
        departmentName = area.name,
        salary = salary,
        experience = experience?.name,
        employment = employment?.name,
        workFormat = schedule?.name,
        description = description,
        companyIcon = employer?.logoUrl,
        companyName = employer?.name,
        keySkills = keySkills.map { it.name },
        alternateUrl = vacancyUrl
    )
}

fun String?.toSalary(): Salary? {
    if (this.isNullOrBlank() || this == "Зарплата не указана") return null

    val regex = Regex("""(\d[\d\s]*)""")
    val numbers = regex.findAll(this).map { it.value.replace(" ", "").toIntOrNull() }.filterNotNull().toList()

    val currencySymbol = VacancyUtils.getCurrencySymbol(this.replace(Regex("[\\d\\sотдо]+"), "").trim())

    return when (numbers.size) {
        2 -> {
            Salary(from = numbers[0], to = numbers[1], currency = currencySymbol)
        }
        1 -> {
            if (this.contains("от")) {
                Salary(from = numbers[0], to = null, currency = currencySymbol)
            } else {
                Salary(from = null, to = numbers[0], currency = currencySymbol)
            }
        }
        else -> null
    }
}
