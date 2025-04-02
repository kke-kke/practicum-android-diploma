package ru.practicum.android.diploma.ui.filters


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentJobPlaceFilterBinding
import ru.practicum.android.diploma.presentation.filters.FilterViewModel
import ru.practicum.android.diploma.ui.BaseFragment


class JobPlaceFilterFragment : BaseFragment<FragmentJobPlaceFilterBinding>() {


    private val filterViewModel: FilterViewModel by activityViewModel()


    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJobPlaceFilterBinding {
        return FragmentJobPlaceFilterBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        filterViewModel.draftFilters.observe(viewLifecycleOwner) { filters ->
            binding.countryTextView.setText(filters.areaName)
            binding.regionTextView.setText(filters.areaParentName)
        }


        initClickListeners()
        initTextChangeListeners()


    }


    private fun initClickListeners() {
        binding.jobPlaceFilterToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        binding.countryTextView.setOnClickListener {
            goToCountryFilterFragment()
        }


        binding.regionTextView.setOnClickListener {
            goToRegionFilterFragment()
        }
    }


    private fun initTextChangeListeners() {
        binding.countryTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit


            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setArrowIcon(binding.countryTextInputLayout, onClickAction = ::goToCountryFilterFragment)
                } else {
                    setClearIcon(binding.countryTextInputLayout)
                }
            }


        })


        binding.regionTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit


            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setArrowIcon(binding.regionTextInputLayout, onClickAction = ::goToRegionFilterFragment)
                } else {
                    setClearIcon(binding.regionTextInputLayout)
                }
            }


        })
    }


    private fun setArrowIcon(textInputLayout: TextInputLayout, onClickAction: () -> Unit) {
        textInputLayout.apply {
            endIconMode = TextInputLayout.END_ICON_NONE
            endIconMode = TextInputLayout.END_ICON_CUSTOM
            setEndIconDrawable(R.drawable.arrow_forward)
            setEndIconOnClickListener { onClickAction() }
        }
    }


    private fun setClearIcon(textInputLayout: TextInputLayout) {
        textInputLayout.apply {
            endIconMode = TextInputLayout.END_ICON_NONE
            endIconMode = TextInputLayout.END_ICON_CUSTOM
            setEndIconDrawable(R.drawable.ic_close)
            setEndIconOnClickListener { editText?.text?.clear() }
        }
    }


    private fun goToCountryFilterFragment() {
        findNavController().navigate(R.id.action_jobPlaceFilterFragment_to_countryFilterFragment)
    }


    private fun goToRegionFilterFragment() {
        findNavController().navigate(R.id.action_jobPlaceFilterFragment_to_regionFilterFragment)
    }


    override fun onResume() {
        super.onResume()
        filterViewModel.reloadDraftFilters()
    }
}
