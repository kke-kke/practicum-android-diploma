package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favourites.FavouritesViewModel
import ru.practicum.android.diploma.presentation.state.FavouritesScreenState
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.ui.search.VacancyAdapter

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val favouritesViewModel: FavouritesViewModel by viewModel()
    private val vacancyList = ArrayList<Vacancy>()
    private val vacancyAdapter = VacancyAdapter(vacancyList, { vacancy -> /*findNavController().navigate()*/})

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

    private fun renderState(state: FavouritesScreenState){

        fun setViewsVisible(visibilityFlag: Boolean, vararg views: View) {
            views.forEach { it.isVisible = visibilityFlag }
        }

        with(binding){
            when(state){
                is FavouritesScreenState.Content -> {
                    vacancyAdapter.updateVacancyList(state.vacancyList)
                    searchResultRecyclerView.isVisible = true
                    setViewsVisible(false, tvFailedToGetVacanciesList, tvListIsEmpty)
                }
                FavouritesScreenState.Empty -> {
                    tvListIsEmpty.isVisible = true
                    setViewsVisible(false, searchResultRecyclerView, tvFailedToGetVacanciesList)
                }
                FavouritesScreenState.Error -> {
                    tvFailedToGetVacanciesList.isVisible = true
                    setViewsVisible(false, tvListIsEmpty, searchResultRecyclerView)
                }
                FavouritesScreenState.Loading -> {
                    setViewsVisible(false, tvListIsEmpty, searchResultRecyclerView, tvFailedToGetVacanciesList)
                }
            }
        }
    }
}
