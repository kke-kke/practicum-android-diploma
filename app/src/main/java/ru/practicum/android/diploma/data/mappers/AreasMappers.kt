package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.models.AreasDTO
import ru.practicum.android.diploma.domain.models.AreaExtended

fun AreasDTO.toDomain(): AreaExtended {
    return AreaExtended(
        id = this.id ?: "",
        name = this.name ?: "",
        parentId = this.parentID,
        areas = this.areas?.map { it.toDomain() } ?: emptyList()
    )
}

fun List<AreasDTO>.toDomainList(): List<AreaExtended> {
    return this.map { it.toDomain() }
}
