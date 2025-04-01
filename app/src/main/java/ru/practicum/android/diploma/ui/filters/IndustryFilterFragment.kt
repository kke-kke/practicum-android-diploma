package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.state.IndustryScreenState
import ru.practicum.android.diploma.presentation.filters.IndustryViewModel
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.hideKeyboard
import ru.practicum.android.diploma.util.showCustomSnackBar

class IndustryFilterFragment : BaseFragment<FragmentIndustryFilterBinding>() {

    private val industryName: Industry by lazy {
        arguments?.getSerializable("industry") as Industry
    }
    private val viewModel: IndustryViewModel by viewModel { parametersOf(industryName) }

    private val adapter: IndustryAdapter by lazy {
        IndustryAdapter(viewModel, clickListener = { industry -> showIndustryDetail(industry) })
    }

    private var resultBundle: Bundle? = null
    private fun showIndustryDetail(industry: Industry) {
        binding.selectButton.isVisible = true
        resultBundle = Bundle().apply {
            putSerializable("industry", industry)
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentIndustryFilterBinding {
        return FragmentIndustryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchIndustryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchIndustryRecyclerView.adapter = adapter

        binding.industryFilterToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchIndustryBar.addTextChangedListener(
            afterTextChanged = { s ->
                if (s.isNullOrEmpty()) {
                    setSearchIcon()
                } else {
                    setClearIcon()
                }
                viewModel.filterIndustries(s.toString())
            }
        )

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustryScreenState.Loading -> showLoading()
                is IndustryScreenState.Success -> showContent(industryList = state.industries)
                is IndustryScreenState.Error -> errorMessageVisibility(isShowUnknownError = true)
            }
        }

        binding.selectButton.setOnClickListener {
            resultBundle?.let { setFragmentResult("industryKey", it) }
            findNavController().navigateUp()
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    private fun setSearchIcon() {
        with(binding) {
            industrySearchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
            industrySearchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
            industrySearchBarContainer.setEndIconDrawable(R.drawable.ic_search)
            industrySearchBarContainer.setEndIconOnClickListener(null)
        }
    }

    private fun setClearIcon() {
        with(binding) {
            industrySearchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
            industrySearchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
            industrySearchBarContainer.setEndIconDrawable(R.drawable.ic_close)
            industrySearchBarContainer.setEndIconOnClickListener {
                binding.searchIndustryBar.text?.clear()
                hideKeyboard()
            }
        }
    }

    private fun showLoading() {
        progressBarVisibility(isShown = true)
        errorMessageVisibility()
    }

    private fun progressBarVisibility(isShown: Boolean = false) {
        binding.progressBarContent.isVisible = isShown
    }

    private fun showContent(industryList: List<Industry>) {
        adapter.setItems(industryList.toMutableList())

        progressBarVisibility()
        if (industryList.isEmpty()) {
            errorMessageVisibility(isShowNothingFound = true)
        } else { errorMessageVisibility() }
    }

    private fun errorMessageVisibility(
        isShowNothingFound: Boolean = false,
        isShowUnknownError: Boolean = false
    ) {
        binding.notFound.isVisible = isShowNothingFound
        binding.unknownError.isVisible = isShowUnknownError
        if (isShowUnknownError) {
            showCustomSnackBar(
                getString(R.string.toast_error) + ". " + getString(R.string.failed_to_get_list),
                binding.root,
                requireContext()
            )
        }
    }

}
