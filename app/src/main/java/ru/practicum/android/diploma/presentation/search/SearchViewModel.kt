package ru.practicum.android.diploma.presentation.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.state.VacanciesScreenState
import ru.practicum.android.diploma.util.Constants

class SearchViewModel(
    private val searchVacanciesInteractor: SearchVacanciesInteractor,
    private val context: Application
) : ViewModel() {

    private var searchJob: Job? = null
    private var currentPage: Int = 0
    private var totalPages: Int = 0
    private var lastSearchText: String = ""

    private val _searchScreenState = MutableLiveData<VacanciesScreenState>()
    val searchScreenState: LiveData<VacanciesScreenState> = _searchScreenState
    private var oldList = listOf<Vacancy>()

    fun searchVacancies(searchedText: String) {
        if (searchedText.isEmpty() or (searchedText == lastSearchText)) {
            return
        }

        oldList = emptyList()
        lastSearchText = searchedText
        currentPage = 0
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_IN_MLS)
            searchVacanciesInteractor.searchVacancies(
                text = lastSearchText,
                page = currentPage,
                perPage = Constants.VACANCIES_PER_PAGE
            )?.collect { searchVacanciesResult ->
                val state: VacanciesScreenState = handleState(searchVacanciesResult)
                setScreenState(state)
            }
        }
    }

    fun getNextPartOfVacancies() {
        if (lastSearchText.isEmpty() or (searchJob?.isActive == true) or (currentPage + 1 > totalPages)) {
            return
        }

        searchJob = viewModelScope.launch {
            currentPage += 1
            searchVacanciesInteractor.searchVacancies(
                text = lastSearchText,
                page = currentPage,
                perPage = Constants.VACANCIES_PER_PAGE
            )?.collect { searchVacanciesResult ->
                val state: VacanciesScreenState = handleState(searchVacanciesResult)
                setScreenState(state)
            }
        }
    }

    private fun setScreenState(newState: VacanciesScreenState) {
        Log.e("1!!!!!!!!!!!", newState.toString())
        _searchScreenState.postValue(newState)
    }

        private fun handleState(searchVacanciesResult: SearchVacanciesResult): VacanciesScreenState {
        val state: VacanciesScreenState =
            when (searchVacanciesResult) {
                is SearchVacanciesResult.Loading -> VacanciesScreenState.Loading
                is SearchVacanciesResult.NetworkError -> VacanciesScreenState.NetworkError(
                    errorText = context.getString(R.string.no_internet)
                )

                is SearchVacanciesResult.NothingFound -> VacanciesScreenState.NothingFound
                is SearchVacanciesResult.ServerError -> VacanciesScreenState.ServerError(
                    errorText = context.getString(R.string.server_error)
                )

                is SearchVacanciesResult.Success -> {
                    totalPages = searchVacanciesResult.vacanciesFound.maxPages
                    oldList = oldList + searchVacanciesResult.vacanciesFound.vacanciesList
                    VacanciesScreenState.Content(
                        vacancyList = oldList,
                        foundVacanciesCount = searchVacanciesResult.vacanciesFound.found
                    )
                }
            }
        return state
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MLS = 2000L
    }
}
