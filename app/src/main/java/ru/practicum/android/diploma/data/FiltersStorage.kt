package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.domain.FilterParameters

interface FiltersStorage {
    fun getFilters(): FilterParameters?
    fun putFilters(filters: FilterParameters)
    fun clear()
}
