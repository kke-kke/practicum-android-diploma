package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentJobPlaceFilterBinding
import ru.practicum.android.diploma.ui.BaseFragment

class JobPlaceFilterFragment : BaseFragment<FragmentJobPlaceFilterBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJobPlaceFilterBinding {
        return FragmentJobPlaceFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setArrowIcon(binding.countryTextInputLayout, onClickAction = ::goToCountryFilterFragment)
                } else {
                    setClearIcon(binding.countryTextInputLayout, binding.countryTextView)
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
                    setClearIcon(binding.regionTextInputLayout, binding.regionTextView)
                }
            }

        })
    }

    private fun setArrowIcon(textInputLayout: TextInputLayout, onClickAction: () -> Unit) {
        textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
        textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        textInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward)
        textInputLayout.setEndIconOnClickListener {
            onClickAction()
        }
    }

    private fun setClearIcon(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText) {
        textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
        textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        textInputLayout.setEndIconDrawable(R.drawable.ic_close)
        textInputLayout.setEndIconOnClickListener {
            textInputEditText.text?.clear()
        }
    }

    private fun goToCountryFilterFragment() {
        findNavController().navigate(R.id.action_jobPlaceFilterFragment_to_countryFilterFragment)
    }

    private fun goToRegionFilterFragment() {
        findNavController().navigate(R.id.action_jobPlaceFilterFragment_to_regionFilterFragment)
    }

}
