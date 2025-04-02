package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.AreasInteractor
import ru.practicum.android.diploma.domain.models.AreaExtended
import ru.practicum.android.diploma.presentation.state.RegionScreenState

class RegionViewModel(
    private val areasInteractor: AreasInteractor
) : ViewModel() {
    private val _regionsState = MutableLiveData<RegionScreenState>()
    val regionsState: LiveData<RegionScreenState> = _regionsState

    private var originalRegions: List<AreaExtended> = emptyList()
    private val _selectedRegion = MutableLiveData<AreaExtended?>(null)

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            areasInteractor.loadAllAreas()
                .fold(
                    onSuccess = { hierarchicalRegions ->
                        originalRegions = flattenRegions(hierarchicalRegions)
                        _regionsState.value = RegionScreenState.Success(originalRegions)
                    },
                    onFailure = {
                        _regionsState.value = RegionScreenState.Error
                    }
                )
        }
    }

    private fun flattenRegions(regions: List<AreaExtended>): List<AreaExtended> {
        val result = mutableListOf<AreaExtended>()
        fun flatten(region: AreaExtended) {
            if (region.parentId != null) {
                result.add(region.copy(areas = emptyList()))
            }
            region.areas.forEach { child -> flatten(child) }
        }
        regions.forEach { root -> flatten(root) }
        return result
    }

    fun filterRegions(query: String) {
        val filtered = if (query.isBlank()) {
            originalRegions
        } else {
            originalRegions.filter { it.name.contains(query, ignoreCase = true) }
        }
        _regionsState.value = if (filtered.isEmpty()) {
            RegionScreenState.Error
        } else {
            RegionScreenState.Success(filtered)
        }
    }

    fun selectRegion(region: AreaExtended?) {
        _selectedRegion.value = region
    }
}
