package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.interactor.AreasInteractor
import ru.practicum.android.diploma.domain.models.AreaExtended

class RegionViewModel(
    private val areasInteractor: AreasInteractor
) : ViewModel() {
    private val _regionsState = MutableLiveData<RegionScreenState>()
    val regionsState: LiveData<RegionScreenState> = _regionsState

    private var currentRegions: List<AreaExtended> = emptyList()

    fun loadRegionsForCountry(country: AreaExtended) {
        currentRegions = country.areas
        _regionsState.value = if (currentRegions.isNotEmpty()) {
            RegionScreenState.Success(currentRegions)
        } else {
            RegionScreenState.Error
        }
    }

    fun searchRegions(query: String) {
        val filteredRegions = currentRegions.filter { it.name.contains(query, ignoreCase = true) }
        _regionsState.value = if (filteredRegions.isEmpty()) {
            RegionScreenState.Error
        } else {
            RegionScreenState.Success(filteredRegions)
        }
    }

}
