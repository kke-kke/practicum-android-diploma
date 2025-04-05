package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.state.IndustryScreenState

class IndustryViewModel(
    private val industryName: Industry,
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<IndustryScreenState>()
    val screenState: LiveData<IndustryScreenState> = _screenState

    private var originalList: List<Industry> = emptyList()

    init {
        loadIndustries()
    }

    private val _selectedIndustryId = MutableLiveData<String?>()
    val selectedIndustryId: LiveData<String?> get() = _selectedIndustryId

    fun selectIndustry(id: String?) {
        _selectedIndustryId.value = id
    }

    private fun loadIndustries() {
        _screenState.value = IndustryScreenState.Loading
        viewModelScope.launch {
            val result = industryInteractor.loadIndustries()
            result.fold(
                onSuccess = { hierarchicalIndustries ->
                    originalList = flattenIndustries(hierarchicalIndustries)
                    _screenState.value = IndustryScreenState.Success(originalList)
                    industryName.id?.let { selectIndustry(it) }
                },
                onFailure = {
                    _screenState.value = IndustryScreenState.Error
                }
            )
        }
    }

    private fun flattenIndustries(industries: List<Industry>): List<Industry> {
        val result = mutableListOf<Industry>()
        fun flatten(industry: Industry) {
            result.add(industry.copy(industries = emptyList()))
            industry.industries.forEach { child -> flatten(child) }
        }
        industries.forEach { root -> flatten(root) }
        return result
    }

    fun filterIndustries(query: String) {
        val current = originalList
        if (query.isBlank()) {
            _screenState.value = IndustryScreenState.Success(current)
        } else {
            val filtered = current.filter { it.name.contains(query, ignoreCase = true) }
            _screenState.value = IndustryScreenState.Success(filtered)
        }
    }
}
