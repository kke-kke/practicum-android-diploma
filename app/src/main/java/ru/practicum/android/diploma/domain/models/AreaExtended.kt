package ru.practicum.android.diploma.domain.models

data class AreaExtended(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<AreaExtended> = emptyList()
) {
    val isCountry: Boolean
        get() = parentId == null
}
