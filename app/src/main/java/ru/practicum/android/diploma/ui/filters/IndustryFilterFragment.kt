package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.hideKeyboard

class IndustryFilterFragment : BaseFragment<FragmentIndustryFilterBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentIndustryFilterBinding {
        return FragmentIndustryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
        )

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

}
