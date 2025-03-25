package ru.practicum.android.diploma.presentation.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavouritesScreenState {
    class Content(val vacancyList: List<Vacancy>): FavouritesScreenState
    data object Empty: FavouritesScreenState
    data object Loading: FavouritesScreenState
    data object Error: FavouritesScreenState
}
