package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FilterFragment : BaseFragment<FragmentFilterBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

}
