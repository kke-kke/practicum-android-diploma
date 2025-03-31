package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.AreaExtended

sealed class CountryScreenState {
    data object Loading : CountryScreenState()
    data class Success(val countries: List<AreaExtended>) : CountryScreenState()
    data object Error : CountryScreenState()
}
