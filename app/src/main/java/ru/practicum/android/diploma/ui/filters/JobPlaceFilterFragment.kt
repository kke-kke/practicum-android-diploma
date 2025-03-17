package ru.practicum.android.diploma.ui.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentJobPlaceFilterBinding
import ru.practicum.android.diploma.ui.BaseFragment

class JobPlaceFilterFragment : BaseFragment<FragmentJobPlaceFilterBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJobPlaceFilterBinding {
        return FragmentJobPlaceFilterBinding.inflate(inflater, container, false)
    }

}
