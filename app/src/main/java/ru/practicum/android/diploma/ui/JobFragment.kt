package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.fragment.app.Fragment

import ru.practicum.android.diploma.databinding.FragmentJobBinding

class JobFragment : Fragment() {

    private var _binding: FragmentJobBinding? = null
    private val jobBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJobBinding.inflate(inflater, container, false)
        return jobBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
