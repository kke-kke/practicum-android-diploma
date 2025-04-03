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
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.FilterViewModel
import ru.practicum.android.diploma.ui.BaseFragment

class FilterFragment : BaseFragment<FragmentFilterBinding>() {
    private var textWatcher: TextWatcher? = null
    private val viewModel: FilterViewModel by activityViewModel()
    private var isSalaryUpdating = false

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.draftFilters.observe(viewLifecycleOwner) { filters ->
            binding.cbDontShowWithoutSalary.isChecked = filters.onlyWithSalary
        }

        with(binding) {
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

            cbDontShowWithoutSalary.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateFilter(
                    viewModel.draftFilters.value?.copy(onlyWithSalary = isChecked)
                        ?: FilterParameters.defaultFilters.copy(onlyWithSalary = isChecked)
                )
            }
        }

        initOnClickListeners()
        setupTextWatcher()
        setupObservers()
        getResultFromIndustryFragment()
    }

    private fun setupObservers() {
        viewModel.draftFilters.observe(viewLifecycleOwner) { filters ->
            val isWorkPlaceChosen = filters.areaName.isNotEmpty() || filters.areaParentName.isNotEmpty()
            val isIndustryChosen = filters.industryName.isNotEmpty()
            val hasChanges = filters != FilterParameters.defaultFilters

            filters.salary?.let { salary ->
                if (salary > 0 && !isSalaryUpdating) {
                    isSalaryUpdating = true
                    binding.etSalary.setText(salary.toString())
                    binding.etSalary.setSelection(salary.toString().length)
                    isSalaryUpdating = false
                }
            }

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
                imgClearWpl.isVisible = isWorkPlaceChosen
                imgClearIndustry.isVisible = isIndustryChosen
            }
        }

        viewModel.showApplyButton.observe(viewLifecycleOwner) { show ->
            binding.btnApply.isVisible = show
        }
    }

    private fun initOnClickListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            imgClearWpl.setOnClickListener { clearWplFilter() }
            imgClearIndustry.setOnClickListener { clearIndustryFilter() }
            industryKeyboard()
            tvWplChoose.setOnClickListener { navJobPlace() }
            tvIndustryChoose.setOnClickListener { navIndustry() }
            viewIndustryChoose.setOnClickListener { navIndustryData() }
            industryVisibility()
            tvReset.setOnClickListener { viewModel.clearDraft() }
            btnApply.setOnClickListener { viewModel.applyFilters(); findNavController().navigateUp() }
        }
    }

    private fun clearWplFilter() {
        viewModel.updateFilter(wplFilter())
    }

    private fun clearIndustryFilter() {
        viewModel.updateFilter(indFilter())
    }

    private fun navJobPlace() {
        findNavController().navigate(R.id.action_filterFragment_to_jobPlaceFragment)
    }

    private fun navIndustry() {
        findNavController().navigate(R.id.action_filterFragment_to_industryFilterFragment)
    }

    private fun navIndustryData() {
        findNavController().navigate(R.id.action_filterFragment_to_industryFilterFragment, indBundle())
    }

    private fun wplFilter() = viewModel.draftFilters.value?.copy(areaId = null, areaName = "", areaParentName = "") ?: FilterParameters.defaultFilters.copy(areaId = null, areaName = "", areaParentName = "")

    private fun indFilter() = viewModel.draftFilters.value?.copy(industryId = null, industryName = "") ?: FilterParameters.defaultFilters.copy(industryId = null, industryName = "")

    private fun indBundle() = Bundle().apply { putSerializable("industry", viewModel.draftFilters.value?.industryName?.let { Industry(id = viewModel.draftFilters.value?.industryId, name = it) }) }

    private fun industryKeyboard() {
        binding.imgClear.setOnClickListener {
            binding.etSalary.setText("")
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
            binding.etSalary.clearFocus()
        }
    }

    private fun industryVisibility() {
        binding.imgClearIndustry.setOnClickListener {
            binding.tvIndustryChoose.visibility = View.VISIBLE
            binding.viewIndustryChoose.visibility = View.GONE
            viewModel.updateFilter(
                viewModel.draftFilters.value?.copy(industryId = null, industryName = "")
                    ?: FilterParameters.defaultFilters.copy(
                        industryId = null,
                        industryName = ""
                    )
            )
        }
    }

    private fun setupTextWatcher() {
        binding.etSalary.setRawInputType(android.text.InputType.TYPE_CLASS_NUMBER)
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgClear.isVisible = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                if (isSalaryUpdating) return
                val salaryText = s?.toString()?.replace(Regex("[^0-9]"), "")
                val salary = salaryText?.toIntOrNull()
                viewModel.updateFilter(
                    viewModel.draftFilters.value?.copy(salary = salary)
                        ?: FilterParameters.defaultFilters.copy(salary = salary)
                )
            }
        }
        binding.etSalary.addTextChangedListener(textWatcher)
    }

    private fun getResultFromIndustryFragment() {
        setFragmentResultListener("industryKey") { _, bundle ->
            val industry = bundle.getSerializable("industry") as? Industry
            if (industry != null) {
                binding.tvIndustryChoose.visibility = View.GONE
                binding.viewIndustryChoose.visibility = View.VISIBLE
                binding.tvIndustry.text = industry.name
                viewModel.updateFilter(
                    viewModel.draftFilters.value?.copy(
                        industryId = industry.id,
                        industryName = industry.name
                    ) ?: FilterParameters.defaultFilters.copy(
                        industryId = industry.id,
                        industryName = industry.name
                    )
                )
            }
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
