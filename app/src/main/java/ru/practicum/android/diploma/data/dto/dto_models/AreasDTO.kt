package ru.practicum.android.diploma.data.dto.dto_models

data class AreasResponse(
    val items: List<AreasDTO>
)
data class AreasDTO(
    val id: String,
    val name: String,
    val areas: List<AreasDTO>?
)
