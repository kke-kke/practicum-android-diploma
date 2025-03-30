package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FilterInteractor {
    fun getCurrentFilter(): FilterParameters
    fun saveFilter(params: FilterParameters)
    fun clearFilter()
}
