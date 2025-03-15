package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding

class IndustryFilterFragment : BaseFragment<FragmentIndustryFilterBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentIndustryFilterBinding {
        return FragmentIndustryFilterBinding.inflate(inflater, container, false)
    }

}
