package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad
import ru.practicum.android.diploma.presentation.state.VacancyDetailsScreenState

class VacancyDetailsViewModel(
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val vacancyDetailsScreenState = MutableLiveData<VacancyDetailsScreenState>()
    fun observeScreenState(): LiveData<VacancyDetailsScreenState> = vacancyDetailsScreenState

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private var currentVacancy: Vacancy? = null

    fun loadVacancy(vacancyId: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor.loadVacancyDetails(vacancyId).collect { state ->
                vacancyDetailsScreenState.postValue(mapStateToScreenState(state))

                val vacancy = state.vacancy
                if (vacancy != null) {
                    currentVacancy = vacancy

                    favoritesInteractor.isFavoriteVacancyFlow(vacancyId)
                        .onEach { fav -> _isFavorite.value = fav }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    private fun mapStateToScreenState(state: VacancyDetailsStateLoad): VacancyDetailsScreenState {
        return when {
            state.isLoading -> VacancyDetailsScreenState.Loading
            state.isNotFoundError -> VacancyDetailsScreenState.Error.NotFoundError
            state.isNetworkError -> VacancyDetailsScreenState.Error.NoInternetError
            state.isOtherError -> VacancyDetailsScreenState.Error.OtherError
            state.vacancy != null -> VacancyDetailsScreenState.Success(state.vacancy)
            else -> VacancyDetailsScreenState.Error.NotFoundError
        }
    }

    fun toggleFavorite() {
        val vacancy = currentVacancy ?: return
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                favoritesInteractor.removeVacancyFromFavorites(vacancy.id)
            } else {
                favoritesInteractor.addVacancyToFavorites(vacancy)
            }
        }
    }
}
