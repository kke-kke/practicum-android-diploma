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
    private var currentPage: Byte = -1


    private val searchScreenState = MutableLiveData<SearchVacanciesResult>()
    fun observeScreenState(): LiveData<SearchVacanciesResult> = searchScreenState

    private var lastSearchedText: String = ""

    fun searchVacancies(searchedText: String) {
        if (searchedText.isEmpty()) {
            return
        }

        if ((searchedText != lastSearchedText)) {
            currentPage = -1
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_IN_MLS)
            viewModelScope.launch {
                lastSearchedText = searchedText
                val filter = mapOf(
                    "text" to searchedText,
                    "page" to (currentPage + 1).toString(),
                    "per_page" to Constants.VACANCIES_PER_PAGE.toString(),
                )

                searchVacanciesInteractor.searchVacancies(filter)
                    ?.collect { searchVacanciesResult ->
                        searchScreenState.postValue(searchVacanciesResult)
                    }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MLS = 2000L
    }
}
