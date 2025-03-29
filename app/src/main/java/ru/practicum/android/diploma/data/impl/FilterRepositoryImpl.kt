package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.data.storage.FiltersStorage
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.FilterParameters

class FilterRepositoryImpl(
    private val storage: FiltersStorage
) : FilterRepository {

    override fun getFilterParameters(): FilterParameters {
        return storage.getFilterParameters()
    }

    override fun saveFilterParameters(params: FilterParameters) {
        storage.saveFilterParameters(params)
    }

    override fun clearFilterParameters() {
        storage.clearFilterParameters()
    }
}
