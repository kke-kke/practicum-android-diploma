package ru.practicum.android.diploma.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.presentation.state.FavouritesScreenState

class FavouritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesScreenState>(FavouritesScreenState.Loading)
    val uiState: StateFlow<FavouritesScreenState> = _uiState

    init {
        favoritesInteractor.getFavoriteVacancies()
            .onStart {
                _uiState.value = FavouritesScreenState.Loading
            }
            .catch {
                _uiState.value = FavouritesScreenState.Error
            }
            .onEach { list ->
                if (list.isEmpty()) {
                    _uiState.value = FavouritesScreenState.Empty
                } else {
                    _uiState.value = FavouritesScreenState.Content(list)
                }
            }
            .launchIn(viewModelScope)
    }

    fun removeFromFavorites(vacancyId: String) {
        viewModelScope.launch {
            favoritesInteractor.removeVacancyFromFavorites(vacancyId)
        }
    }
}
