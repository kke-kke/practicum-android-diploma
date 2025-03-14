package ru.practicum.android.diploma.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentJobBinding

class JobFragment : Fragment() {

    private lateinit var jobBinding: FragmentJobBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        jobBinding = FragmentJobBinding.inflate(inflater, container, false)
        return jobBinding.root
    }

}
