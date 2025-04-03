package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.Industry

sealed class IndustryScreenState {
    object Loading : IndustryScreenState()
    data class Success(val industries: List<Industry>) : IndustryScreenState()
    object Error : IndustryScreenState()
}
