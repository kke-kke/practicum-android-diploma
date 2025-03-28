package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
                    setArrowIcon()
                } else {
                    setClearIcon()
                }
            }

        })
    }

    private fun setArrowIcon() {
        binding.regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
        binding.regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.regionSearchBarContainer.setEndIconDrawable(R.drawable.ic_arrow_forward)
        binding.regionSearchBarContainer.setEndIconOnClickListener {
            goToJobPlaceFilterFragment()
        }
    }

    private fun setClearIcon() {
        binding.regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
        binding.regionSearchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.regionSearchBarContainer.setEndIconDrawable(R.drawable.ic_close)
        binding.regionSearchBarContainer.setEndIconOnClickListener {
            binding.regionSearchBar.text?.clear()
        }
    }

    private fun goToJobPlaceFilterFragment() {
        findNavController().navigate(R.id.action_regionFilterFragment_to_jobPlaceFilterFragment)
    }

}
