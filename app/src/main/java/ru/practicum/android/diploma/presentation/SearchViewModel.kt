package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesResult
import ru.practicum.android.diploma.util.Constants

class SearchViewModel(private val searchVacanciesInteractor: SearchVacanciesInteractor) : ViewModel() {

    private var searchJob: Job? = null
    private var currentPage: Int = 0
    private var totalPages: Int = 0
    private var lastSearchText: String = ""

    private val searchScreenState = MutableLiveData<SearchVacanciesResult>()
    fun observeScreenState(): LiveData<SearchVacanciesResult> = searchScreenState

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
                    searchScreenState.postValue(searchVacanciesResult)
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
                    searchScreenState.postValue(searchVacanciesResult)
                }
        }
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
