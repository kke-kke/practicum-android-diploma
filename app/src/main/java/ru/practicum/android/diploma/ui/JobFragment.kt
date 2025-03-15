package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentJobBinding

class JobFragment : BaseFragment<FragmentJobBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJobBinding {
        return FragmentJobBinding.inflate(inflater, container, false)
    }

}
