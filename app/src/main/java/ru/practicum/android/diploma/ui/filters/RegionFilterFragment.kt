package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.ui.BaseFragment

class RegionFilterFragment : BaseFragment<FragmentRegionFilterBinding>() {
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRegionFilterBinding {
        return FragmentRegionFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.regionSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setSearchIcon()
                } else {
                    setClearIcon()
                }
            }

        })
    }

    private fun setSearchIcon() {
        with(binding) {
            regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
            regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
            regionSearchBarContainer.setEndIconDrawable(R.drawable.ic_search)
            regionSearchBarContainer.setEndIconOnClickListener(null)
        }
    }

    private fun setClearIcon() {
        with(binding) {
            regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
            regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
            regionSearchBarContainer.setEndIconDrawable(R.drawable.ic_close)
            regionSearchBarContainer.setEndIconOnClickListener {
                regionSearchBar.text?.clear()
            }
        }
    }

}
