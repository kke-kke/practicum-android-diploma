package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.storage.SharedFiltersInteractor

class FilterViewModel(
    private val filtersInteractor: SharedFiltersInteractor
) : ViewModel() {

    private val _filterParameters = MutableLiveData<FilterParameters>()
    val filterParameters: LiveData<FilterParameters> = _filterParameters

    init {
        _filterParameters.value = filtersInteractor.getCurrentFilters()
    }

    fun updateFilter(updated: FilterParameters) {
        _filterParameters.value = updated
        filtersInteractor.saveAllFilters(updated)
    }

    fun clearFilter() {
        filtersInteractor.clearFilters()
        _filterParameters.value = FilterParameters.defaultFilters
    }
}
