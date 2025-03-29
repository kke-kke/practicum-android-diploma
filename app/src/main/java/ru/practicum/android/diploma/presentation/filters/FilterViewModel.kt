package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterParameters

class FilterViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val _filterParameters = MutableLiveData<FilterParameters>()
    val filterParameters: LiveData<FilterParameters> = _filterParameters

    init {
        _filterParameters.value = filterInteractor.getCurrentFilter()
    }

    fun updateFilter(updated: FilterParameters) {
        _filterParameters.value = updated
        filterInteractor.saveFilter(updated)
    }

    fun clearFilter() {
        filterInteractor.clearFilter()
        _filterParameters.value = FilterParameters(
            salaryFrom = null,
            excludeNoSalary = false,
            industryId = null,
            industryName = null,
            countryId = null,
            countryName = null,
            regionId = null,
            regionName = null
        )
    }
}
