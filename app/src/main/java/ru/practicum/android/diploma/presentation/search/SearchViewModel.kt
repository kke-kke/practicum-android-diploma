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
    private var isNextPageLoading = false

    private val searchScreenState = MutableLiveData<VacanciesScreenState>()
    fun observeScreenState(): LiveData<VacanciesScreenState> = searchScreenState

    fun searchVacancies(searchedText: String) {
        if (searchedText.isEmpty() or (searchedText == lastSearchText)) {
            return
        }

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

        if (!isNextPageLoading) {
            isNextPageLoading = true
            searchJob = viewModelScope.launch {
                searchVacanciesInteractor.searchVacancies(
                    text = lastSearchText,
                    page = currentPage + 1,
                    perPage = Constants.VACANCIES_PER_PAGE
                )?.collect { searchVacanciesResult ->
                    val state: VacanciesScreenState = handleState(searchVacanciesResult, true)
                    setScreenState(state)
                    isNextPageLoading = false

                    if (state is VacanciesScreenState.Content || state is VacanciesScreenState.NothingFound) {
                        currentPage += 1
                    }
                }
            }
        }
    }

    private fun setScreenState(newState: VacanciesScreenState) {
        searchScreenState.postValue(newState)
    }

    private fun handleState(searchVacanciesResult: SearchVacanciesResult, isPaginationLoading: Boolean = false): VacanciesScreenState {
        val state: VacanciesScreenState =
            when (searchVacanciesResult) {
                is SearchVacanciesResult.Loading -> VacanciesScreenState.Loading(isPaginationLoading)
                is SearchVacanciesResult.NetworkError -> VacanciesScreenState.NetworkError(
                    errorText = context.getString(R.string.no_internet),
                    isPaginationLoading = isPaginationLoading
                )

                is SearchVacanciesResult.NothingFound -> VacanciesScreenState.NothingFound(isPaginationLoading)
                is SearchVacanciesResult.ServerError -> VacanciesScreenState.ServerError(
                    errorText = context.getString(R.string.server_error),
                    isPaginationLoading = isPaginationLoading
                )

                is SearchVacanciesResult.Success -> {
                    totalPages = searchVacanciesResult.vacanciesFound.maxPages
                    VacanciesScreenState.Content(
                        vacancyList = searchVacanciesResult.vacanciesFound.vacanciesList,
                        foundVacanciesCount = searchVacanciesResult.vacanciesFound.found,
                        isPaginationLoading = isPaginationLoading
                    )
                }
            }
        return state
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MLS = 2000L
    }
}
