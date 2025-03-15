package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

}
