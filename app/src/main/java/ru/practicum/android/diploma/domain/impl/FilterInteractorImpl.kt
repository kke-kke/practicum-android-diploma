package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterParameters

class FilterInteractorImpl(
    private val filterRepository: FilterRepository
) : FilterInteractor {

    override fun getCurrentFilter(): FilterParameters {
        return filterRepository.getFilterParameters()
    }

    override fun saveFilter(params: FilterParameters) {
        filterRepository.saveFilterParameters(params)
    }

    override fun clearFilter() {
        filterRepository.clearFilterParameters()
    }
}
