package ru.practicum.android.diploma.presentation.search

import android.app.Application
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
            searchVacanciesInteractor.searchVacancies(getFilter())
                ?.collect { searchVacanciesResult ->
                    setScreenState(searchVacanciesResult)
                    if (searchVacanciesResult is SearchVacanciesResult.Success) {
                        totalPages = searchVacanciesResult.vacanciesFound.maxPages
                    }
                }
        }
    }

    fun getNextPartOfVacancies() {
        if (lastSearchText.isEmpty() or (searchJob?.isActive == true) or (currentPage + 1 > totalPages)) {
            return
        }

        searchJob = viewModelScope.launch {
            currentPage += 1
            searchVacanciesInteractor.searchVacancies(getFilter())
                ?.collect { searchVacanciesResult ->
                    setScreenState(searchVacanciesResult)
                }
        }
    }

    private fun setScreenState(searchVacanciesResult: SearchVacanciesResult) {
        val state: VacanciesScreenState = when (searchVacanciesResult) {
            is SearchVacanciesResult.Error -> {
                if (searchVacanciesResult.isNetworkError) {
                    VacanciesScreenState.Error.ConnectionError(context.getString(R.string.failed_to_get_vacancies_list))
                } else if (searchVacanciesResult.isNoInternet) {
                    VacanciesScreenState.Error.NoInternetError(context.getString(R.string.no_internet))
                } else {
                    VacanciesScreenState.Empty(context.getString(R.string.no_internet))
                }
            }
            SearchVacanciesResult.Loading -> {
                VacanciesScreenState.Loading
            }
            is SearchVacanciesResult.Success -> {
                VacanciesScreenState.Content(
                    searchVacanciesResult.vacanciesFound.vacanciesList,
                    searchVacanciesResult.vacanciesFound.found
                )
            }
        }
        searchScreenState.postValue(state)
    }

    private fun getFilter() = mapOf(
        "text" to lastSearchText,
        "page" to currentPage.toString(),
        "per_page" to Constants.VACANCIES_PER_PAGE.toString(),
    )

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MLS = 2000L
    }
}
