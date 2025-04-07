package ru.practicum.android.diploma.data.storage

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FiltersStorage {
    fun getFilters(): FilterParameters?
    fun putFilters(filters: FilterParameters)
    fun clear()
    fun getDraftFilters(): FilterParameters?
    fun putDraftFilters(filters: FilterParameters)
    fun clearDraftFilters()
}
