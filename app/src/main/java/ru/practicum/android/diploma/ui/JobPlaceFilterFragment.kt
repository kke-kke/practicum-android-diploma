package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentJobPlaceFilterBinding

class JobPlaceFilterFragment : Fragment() {

    private var _binding: FragmentJobPlaceFilterBinding? = null
    private val jobPlaceBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJobPlaceFilterBinding.inflate(inflater, container, false)
        return jobPlaceBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
