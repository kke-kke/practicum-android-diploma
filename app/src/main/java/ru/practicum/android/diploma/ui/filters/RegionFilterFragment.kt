package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.domain.models.AreaExtended
import ru.practicum.android.diploma.presentation.filters.FilterViewModel
import ru.practicum.android.diploma.presentation.filters.RegionViewModel
import ru.practicum.android.diploma.presentation.state.RegionScreenState
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.hideKeyboard
import ru.practicum.android.diploma.util.showCustomSnackBar

class RegionFilterFragment : BaseFragment<FragmentRegionFilterBinding>() {

    private val viewModel: RegionViewModel by viewModel()
    private val filterViewModel: FilterViewModel by activityViewModel()
    private val adapter: RegionAdapter by lazy {
        RegionAdapter(viewModel) { region ->
            filterViewModel.updateRegion(region)
            findNavController().navigateUp()
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRegionFilterBinding {
        return FragmentRegionFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setupObservers()
        setupToolbar()
        setupSearch()
    }

    private fun initAdapter() {
        binding.countriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.countriesRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.regionsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionScreenState.Success -> showContent(state.regions)
                RegionScreenState.Error -> showError()
            }
        }
    }

    private fun setupToolbar() {
        binding.countryFilterToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSearch() {
        binding.regionSearchBar.addTextChangedListener { text ->
            text?.toString()?.let { query ->
                viewModel.filterRegions(query)
                if (query.isEmpty()) hideKeyboard()
            }
        }
    }

    private fun showContent(regions: List<AreaExtended>) {
        binding.countriesRecyclerView.isVisible = true
        binding.noRegionList.isVisible = false
        binding.internetError.isVisible = false
        adapter.setItems(regions)
    }

    private fun showError() {
        binding.countriesRecyclerView.isVisible = false
        binding.noRegionList.isVisible = true
        binding.internetError.isVisible = false
        showCustomSnackBar(
            getString(R.string.failed_to_get_list),
            binding.root,
            requireContext()
        )
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }
}
