package ru.practicum.android.diploma.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentJobPlaceFilterBinding

class JobPlaceFilterFragment : Fragment() {

    private lateinit var jobPlaceBinding: FragmentJobPlaceFilterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        jobPlaceBinding = FragmentJobPlaceFilterBinding.inflate(inflater, container, false)
        return jobPlaceBinding.root
    }

}
