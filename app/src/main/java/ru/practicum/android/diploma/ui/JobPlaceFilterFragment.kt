package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentJobPlaceFilterBinding

class JobPlaceFilterFragment : BaseFragment<FragmentJobPlaceFilterBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJobPlaceFilterBinding {
        return FragmentJobPlaceFilterBinding.inflate(inflater, container, false)
    }

}
