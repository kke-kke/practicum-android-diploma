package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentTeamBinding

class TeamFragment : BaseFragment<FragmentTeamBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTeamBinding {
        return FragmentTeamBinding.inflate(inflater, container, false)
    }

}
