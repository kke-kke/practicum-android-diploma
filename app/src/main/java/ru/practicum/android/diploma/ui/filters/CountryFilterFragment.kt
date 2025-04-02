package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.domain.models.AreaExtended
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.presentation.filters.CountryViewModel
import ru.practicum.android.diploma.presentation.filters.FilterViewModel
import ru.practicum.android.diploma.presentation.state.CountryScreenState
import ru.practicum.android.diploma.ui.BaseFragment

class CountryFilterFragment : BaseFragment<FragmentCountryFilterBinding>() {

    private val viewModel: CountryViewModel by viewModel()
    private val filterViewModel: FilterViewModel by viewModel()
    private val countriesAdapter: CountriesAdapter by lazy {
        CountriesAdapter { country -> saveSelectedCountry(country) }
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCountryFilterBinding {
        return FragmentCountryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        viewModel.loadCountries()
    }

    private fun setupRecyclerView() {
        binding.countriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
    }

    private fun saveSelectedCountry(country: AreaExtended) {
        val currentFilters = filterViewModel.draftFilters.value ?: FilterParameters.defaultFilters
        val updatedFilters = currentFilters.copy(
            areaId = country.id,
            areaName = country.name
        )
        filterViewModel.updateFilter(updatedFilters)
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun observeViewModel() {
        viewModel.countriesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryScreenState.Success -> {
                    if (state.countries.isEmpty()) {
                        showErrorView()
                    } else {
                        showCountriesList(state.countries)
                    }
                }
                is CountryScreenState.Error -> showErrorView()
                else -> Unit
            }
        }
    }

    private fun showCountriesList(countries: List<AreaExtended>) {
        binding.countriesRecyclerView.visibility = View.VISIBLE
        binding.notFound.visibility = View.GONE
        countriesAdapter.submitList(countries)
    }

    private fun showErrorView() {
        binding.countriesRecyclerView.visibility = View.GONE
        binding.notFound.visibility = View.VISIBLE
    }
}
