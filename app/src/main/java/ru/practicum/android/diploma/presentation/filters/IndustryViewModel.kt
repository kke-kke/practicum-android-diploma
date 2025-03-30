package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Industry

sealed class IndustryScreenState {
    object Loading : IndustryScreenState()
    data class Success(val industries: List<Industry>) : IndustryScreenState()
    object Error : IndustryScreenState()
}

class IndustryViewModel(
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<IndustryScreenState>()
    val screenState: LiveData<IndustryScreenState> = _screenState

    private var originalList: List<Industry> = emptyList()

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        _screenState.value = IndustryScreenState.Loading
        viewModelScope.launch {
            val result = industryInteractor.loadIndustries()
            result.fold(
                onSuccess = { list ->
                    originalList = list
                    _screenState.value = IndustryScreenState.Success(list)
                },
                onFailure = {
                    _screenState.value = IndustryScreenState.Error
                }
            )
        }
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
