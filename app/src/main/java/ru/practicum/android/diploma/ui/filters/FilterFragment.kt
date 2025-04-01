package ru.practicum.android.diploma.ui.filters

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.FilterViewModel
import ru.practicum.android.diploma.ui.BaseFragment

class FilterFragment : BaseFragment<FragmentFilterBinding>() {

    private var textWatcher: TextWatcher? = null
    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.draftFilters.observe(viewLifecycleOwner) { filters ->
            binding.cbDontShowWithoutSalary.isChecked = filters.onlyWithSalary
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            etSalary.setOnFocusChangeListener { _, hasFocus ->
                val color = if (hasFocus) {
                    ContextCompat.getColor(requireContext(), R.color.yp_blue)
                } else {
                    val typedValue = TypedValue()
                    requireContext().theme.resolveAttribute(
                        com.google.android.material.R.attr.colorOnSecondary,
                        typedValue,
                        true
                    )
                    typedValue.data
                }
                tvExpectedSalary.setTextColor(color)
            }

            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    imgClear.isVisible = !s.isNullOrEmpty()
                }

                override fun afterTextChanged(s: Editable?) = Unit
            }
            etSalary.addTextChangedListener(textWatcher)

            imgClear.setOnClickListener {
                etSalary.setText("")
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
                etSalary.clearFocus()
            }

            tvWplChoose.setOnClickListener {
                findNavController().navigate(R.id.action_filterFragment_to_jobPlaceFragment)
            }

            tvIndustryChoose.setOnClickListener {
                findNavController().navigate(R.id.action_filterFragment_to_industryFilterFragment)
            }


            viewIndustryChoose.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable(
                    "industry",
                    viewModel.draftFilters.value?.industryName?.let { it1 ->
                        Industry(viewModel.draftFilters.value?.industryId,
                            it1
                        )
                    }
                )
                findNavController().navigate(R.id.action_filterFragment_to_industryFilterFragment, bundle)
            }

            imgClearIndustry.setOnClickListener {
                tvIndustryChoose.visibility = View.VISIBLE
                viewIndustryChoose.visibility = View.GONE

                viewModel.updateFilter(
                    viewModel.draftFilters.value?.copy(industryId = null, industryName = "")
                        ?: FilterParameters.defaultFilters.copy(
                            industryId = null,
                            industryName = ""
                        )
                )
            }

            // Получение результата выбора с экрана "Отрасль"
            setFragmentResultListener("industryKey") { _, bundle ->
                val industry = bundle.getSerializable("industry") as? Industry
                if (industry != null) {
                    tvIndustryChoose.visibility = View.GONE
                    viewIndustryChoose.visibility = View.VISIBLE
                    tvIndustry.text = industry.name

                    viewModel.updateFilter(
                        viewModel.draftFilters.value?.copy(industryId = industry.id, industryName = industry.name)
                            ?: FilterParameters.defaultFilters.copy(
                                industryId = industry.id,
                                industryName = industry.name
                            )
                    )
                }
            }

            cbDontShowWithoutSalary.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateFilter(
                    viewModel.draftFilters.value?.copy(onlyWithSalary = isChecked)
                        ?: FilterParameters.defaultFilters.copy(onlyWithSalary = isChecked)
                )
            }

            tvReset.setOnClickListener {
                viewModel.clearDraft()
            }

            btnApply.setOnClickListener {
                viewModel.applyFilters()
                findNavController().navigateUp()
            }

            setupObservers()
        }
    }

    private fun setupObservers() {
        viewModel.draftFilters.observe(viewLifecycleOwner) { filters ->
            val hasCountry = filters.areaName.isNotEmpty()
            val hasRegion = filters.areaParentName.isNotEmpty()
            val isWorkPlaceChosen = hasCountry || hasRegion

            val isIndustryChosen = filters.industryName.isNotEmpty()

            val hasChanges = filters != FilterParameters.defaultFilters

            with(binding) {
                tvWplChoose.isVisible = !isWorkPlaceChosen
                chosenPlaceOfWork.isVisible = isWorkPlaceChosen
                if (isWorkPlaceChosen) {
                    tvWorkplace.text = buildWorkplaceText(filters)
                }

                tvIndustryChoose.isVisible = !isIndustryChosen
                chosenIndustry.isVisible = isIndustryChosen
                if (isIndustryChosen) {
                    tvIndustry.text = filters.industryName
                }

                tvReset.isVisible = hasChanges
            }
        }

        viewModel.showApplyButton.observe(viewLifecycleOwner) { show ->
            binding.btnApply.isVisible = show
        }
    }

    private fun buildWorkplaceText(filters: FilterParameters): String {
        return when {
            filters.areaParentName.isNotEmpty() && filters.areaName.isNotEmpty() ->
                "${filters.areaParentName}, ${filters.areaName}"
            filters.areaName.isNotEmpty() -> filters.areaName
            else -> filters.areaParentName
        }
    }

    override fun onDestroyView() {
        textWatcher?.let {
            binding.etSalary.removeTextChangedListener(it)
        }
        super.onDestroyView()
    }
}
