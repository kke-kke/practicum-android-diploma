package ru.practicum.android.diploma.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding

class IndustryFilterFragment : Fragment() {

    private lateinit var industryFilterBinding: FragmentIndustryFilterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        industryFilterBinding = FragmentIndustryFilterBinding.inflate(inflater, container, false)
        return industryFilterBinding.root
    }

}
