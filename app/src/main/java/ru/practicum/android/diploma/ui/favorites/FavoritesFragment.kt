package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.presentation.favourites.FavouritesViewModel
import ru.practicum.android.diploma.presentation.state.VacanciesScreenState
import ru.practicum.android.diploma.ui.BaseFragment

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val favouritesViewModel: FavouritesViewModel by viewModel()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                favouritesViewModel.uiState.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: VacanciesScreenState){

        fun setViewsVisible(visibilityFlag: Boolean, vararg views: View) {
            views.forEach { it.isVisible = visibilityFlag }
        }

        with(binding){
            /*when(state){
                is VacanciesScreenState.Content -> {
                    //--- Обновление адаптера
                    //---
                    searchResultRecyclerView.isVisible = true
                    setViewsVisible(false, tvFailedToGetVacanciesList, tvListIsEmpty)
                }
                VacanciesScreenState.Empty -> {
                    tvListIsEmpty.isVisible = true
                    setViewsVisible(false, searchResultRecyclerView, tvFailedToGetVacanciesList)
                }
                is VacanciesScreenState.Error -> {
                    tvFailedToGetVacanciesList.isVisible = true
                    setViewsVisible(false, tvListIsEmpty, searchResultRecyclerView)
                }
                VacanciesScreenState.Loading -> {
                    setViewsVisible(false, tvListIsEmpty, searchResultRecyclerView, tvFailedToGetVacanciesList)
                }
            }*/
        }
    }
}
