package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad

class VacancyDetailsViewModel(private val vacancyDetailsInteractor: VacancyDetailsInteractor) : ViewModel() {

    private val vacancyDetailsScreenState = MutableLiveData<VacancyDetailsScreenState>()
    fun observeScreenState(): LiveData<VacancyDetailsScreenState> = vacancyDetailsScreenState

    fun loadVacancy(vacancyId: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor.loadVacancyDetails(vacancyId).collect { state ->
                vacancyDetailsScreenState.postValue(mapStateToScreenState(state))
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

}
