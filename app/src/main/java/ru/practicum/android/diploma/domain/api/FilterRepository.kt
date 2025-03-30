package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterParameters

/**
 * репозиторий для чтения/записи настроек параметров фильтра
 */
interface FilterRepository {
    fun getFilterParameters(): FilterParameters
    fun saveFilterParameters(params: FilterParameters)
    fun clearFilterParameters()
}
