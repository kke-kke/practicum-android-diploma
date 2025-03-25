package ru.practicum.android.diploma.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.presentation.state.FavouritesScreenState

class FavouritesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesScreenState>(FavouritesScreenState.Loading)
    val uiState: StateFlow<FavouritesScreenState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = FavouritesScreenState.Loading
            /*favouritesUseCase.getFavouriteVacancies().collect { state ->
                //--- Обработка состояния
            }
        }*/
        }
    }
}
