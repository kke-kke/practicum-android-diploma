package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.AreasInteractor
import ru.practicum.android.diploma.domain.models.AreaExtended

class CountryViewModel(
    private val areasInteractor: AreasInteractor
) : ViewModel() {

    private val _countriesState = MutableLiveData<CountryScreenState>()
    val countriesState: LiveData<CountryScreenState> = _countriesState

    private var allCountries: List<AreaExtended> = emptyList()

    fun loadCountries() {
        _countriesState.value = CountryScreenState.Loading
        viewModelScope.launch {
            areasInteractor.loadAllAreas().fold(
                onSuccess = { areas ->
                    allCountries = areas.filter { it.isCountry }
                    _countriesState.value = CountryScreenState.Success(allCountries)
                },
                onFailure = {
                    _countriesState.value = CountryScreenState.Error
                }
            )
        }
    }
}
