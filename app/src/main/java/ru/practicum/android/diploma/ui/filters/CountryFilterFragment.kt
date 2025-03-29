package ru.practicum.android.diploma.ui.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.ui.BaseFragment

class CountryFilterFragment : BaseFragment<FragmentCountryFilterBinding>() {
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCountryFilterBinding {
        return FragmentCountryFilterBinding.inflate(inflater, container, false)
    }
}
