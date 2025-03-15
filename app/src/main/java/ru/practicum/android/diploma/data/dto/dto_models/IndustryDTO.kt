package ru.practicum.android.diploma.data.dto.dto_models

data class IndustryDTO(
    val id: String,
    val name: String,
    val industries: List<IndustryDTO>?
)
