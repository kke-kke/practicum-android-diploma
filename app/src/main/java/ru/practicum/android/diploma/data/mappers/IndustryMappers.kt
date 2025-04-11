package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.models.IndustryDTO
import ru.practicum.android.diploma.domain.models.Industry

fun IndustryDTO.toDomain(): Industry {
    return Industry(
        id = this.id ?: "",
        name = this.name ?: "",
        industries = this.industries?.map { it.toDomain() } ?: emptyList()
    )
}

fun List<IndustryDTO>.toDomainList(): List<Industry> {
    return this.map { it.toDomain() }
}
