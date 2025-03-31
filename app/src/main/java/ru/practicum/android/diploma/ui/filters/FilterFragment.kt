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
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.ui.BaseFragment

class FilterFragment : BaseFragment<FragmentFilterBinding>() {

    private var textWatcher: TextWatcher? = null

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        }
    }

    override fun onDestroyView() {
        textWatcher?.let {
            binding.etSalary.removeTextChangedListener(it)
        }
        super.onDestroyView()
    }

}
