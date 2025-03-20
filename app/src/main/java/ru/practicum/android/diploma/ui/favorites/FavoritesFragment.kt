package ru.practicum.android.diploma.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.ui.BaseFragment

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
    }

}
