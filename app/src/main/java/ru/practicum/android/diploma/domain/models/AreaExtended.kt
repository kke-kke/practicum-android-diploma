package ru.practicum.android.diploma.domain.models

/**
 * Расширенная доменная модель для стран/регионов, которую мы получаем из AreasDTO.
 * Считаем "страной" тот объект, у которого parentId = null.
 */
data class AreaExtended(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<AreaExtended> = emptyList()
) {
    /**
     * Функция, определяющая, является ли этот Area "страной".
     */
    fun isCountry(): Boolean = parentId == null
}
