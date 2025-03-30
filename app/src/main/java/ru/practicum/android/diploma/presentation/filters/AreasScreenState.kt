package ru.practicum.android.diploma.presentation.filters

import ru.practicum.android.diploma.domain.models.AreaExtended

sealed class AreasScreenState {
    data object Loading : AreasScreenState()
    data class Success(val areas: List<AreaExtended>) : AreasScreenState()
    data object Error : AreasScreenState()
}
