package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(/*--- Здесть будет useCase. Потом не забыть добавить в di ---*/) : ViewModel() {

    private var searchJob: Job? = null

    //private val searchScreenState = MutableLiveData<ScreenState>()
    //fun observeScreenState(): LiveData<ScreenState> = searchTracksState

    private var lastSearchedText: String = ""

    fun searchVacancies(searchedText: String) {
        if ((searchedText == lastSearchedText) or (searchedText.isEmpty())) {
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_IN_MLS)
            //--- setScreenState(ScreenState.Loading)
            viewModelScope.launch {
                lastSearchedText = searchedText
                /*searchUseCase.searchVacancies(searchedText)
                    .collect {
                        //--- Здесь будет установка состояния (ошибка, данные.....)
                        setScreenState(state)
                    }*/
            }

        }
    }

    /*private fun setScreenState(state: ScreenState) {
        searchScreenState.postValue(state)
    }*/

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MLS = 2000L
    }
}
