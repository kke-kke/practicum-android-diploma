package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.storage.SharedFiltersInteractor

class FilterViewModel(
    private val filtersInteractor: SharedFiltersInteractor
) : ViewModel() {

    private val _draftFilters = MutableLiveData<FilterParameters>()
    private val _currentFilters = MutableLiveData<FilterParameters>()
    private val _showApplyButton = MutableLiveData<Boolean>()

    val draftFilters: LiveData<FilterParameters> = _draftFilters
    val showApplyButton: LiveData<Boolean> = _showApplyButton

    init {
        val savedFilters = filtersInteractor.getCurrentFilters()
        val draftFilters = filtersInteractor.getDraftFilters()

        _currentFilters.value = savedFilters
        _draftFilters.value = draftFilters
        updateApplyButtonState()
    }

    fun updateFilter(updated: FilterParameters) {
        _draftFilters.value = updated
        filtersInteractor.saveDraftFilters(updated)
        updateApplyButtonState()
    }

    fun applyFilters() {
        val draft = _draftFilters.value ?: return
        filtersInteractor.saveAllFilters(draft)
        _currentFilters.value = filtersInteractor.getCurrentFilters()
    }

    fun reloadDraftFilters() {
        val filters = filtersInteractor.getDraftFilters()
        _draftFilters.value = filters
        updateApplyButtonState()
    }

    private fun updateApplyButtonState() {
        val areDifferent = _draftFilters.value != _currentFilters.value
        _showApplyButton.value = areDifferent
    }

    fun clearDraft() {
        filtersInteractor.clearDraftFilters()
        _draftFilters.value = FilterParameters.defaultFilters
    }
}
