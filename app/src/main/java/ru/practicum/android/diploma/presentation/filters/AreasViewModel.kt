package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.AreasInteractor
import ru.practicum.android.diploma.domain.models.AreaExtended

class AreasViewModel(
    private val areasInteractor: AreasInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<AreasScreenState>()
    val screenState: LiveData<AreasScreenState> = _screenState

    private var allCountries: List<AreaExtended> = emptyList()
    private var currentRegions: List<AreaExtended> = emptyList()

    fun loadCountries() {
        _screenState.value = AreasScreenState.Loading
        viewModelScope.launch {
            areasInteractor.loadAllAreas().fold(
                onSuccess = { areas ->
                    allCountries = areas.filter { it.isCountry }
                    _screenState.value = AreasScreenState.Success(allCountries)
                },
                onFailure = {
                    _screenState.value = AreasScreenState.Error
                }
            )
        }
    }

    fun loadRegionsForCountry(countryId: String) {
        allCountries.find { it.id == countryId }?.let { country ->
            currentRegions = country.areas
            _screenState.value = AreasScreenState.Success(currentRegions)
        } ?: run {
            _screenState.value = AreasScreenState.Error
        }
    }

    fun searchRegions(query: String) {
        viewModelScope.launch {
            Result.success(currentRegions)
                .map { regions ->
                    regions.filter { it.name.contains(query, ignoreCase = true) }
                }
                .fold(
                    onSuccess = { filteredRegions ->
                        _screenState.value = if (filteredRegions.isEmpty()) {
                            AreasScreenState.Error
                        } else {
                            AreasScreenState.Success(filteredRegions)
                        }
                    },
                    onFailure = {
                        _screenState.value = AreasScreenState.Error
                    }
                )
        }
    }
}
