package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.AreaExtended

sealed class RegionScreenState {
    data class Success(val regions: List<AreaExtended>) : RegionScreenState()
    data object Error : RegionScreenState()
}
