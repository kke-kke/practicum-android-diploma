package ru.practicum.android.diploma.presentation.vacancydetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad

class VacancyDetailsViewModel(private val vacancyDetailsInteractor: VacancyDetailsInteractor) : ViewModel() {

    private val vacancyDetailsScreenState = MutableLiveData<VacancyDetailsScreenState>()
    fun observeScreenState(): LiveData<VacancyDetailsScreenState> = vacancyDetailsScreenState

    fun loadVacancy(vacancyId: String, context: Context) {
        viewModelScope.launch {
            vacancyDetailsInteractor.loadVacancyDetails(vacancyId).collect { state ->
                vacancyDetailsScreenState.postValue(mapStateToScreenState(state, context))
            }
        }
    }
    private fun mapStateToScreenState(state: VacancyDetailsStateLoad, context: Context): VacancyDetailsScreenState {
        return when {
            state.isLoading -> VacancyDetailsScreenState.Loading
            state.isNotFoundError -> VacancyDetailsScreenState.Error.NotFoundError(context.getString(R.string.vacancy_not_found))
            state.isNetworkError -> VacancyDetailsScreenState.Error.NoInternetError(context.getString(R.string.no_internet))
            state.isOtherError -> VacancyDetailsScreenState.Error.OtherError(context.getString(R.string.failed_to_get_vacancies_list))
            state.vacancy != null -> VacancyDetailsScreenState.Success(state.vacancy)
            else -> VacancyDetailsScreenState.Error.NotFoundError(context.getString(R.string.vacancy_not_found))
        }
    }

}
