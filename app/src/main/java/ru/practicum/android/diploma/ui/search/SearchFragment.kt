package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.ui.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setSearchIcon()
                    hideRecycler()
                } else {
                    setClearIcon()
                }
            }

        })

    }

    private fun setSearchIcon() {
        binding.searchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
        binding.searchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.searchBarContainer.setEndIconDrawable(R.drawable.ic_search)
        binding.searchBarContainer.setEndIconOnClickListener(null)
    }

    private fun setClearIcon() {
        binding.searchBarContainer.endIconMode = TextInputLayout.END_ICON_NONE
        binding.searchBarContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.searchBarContainer.setEndIconDrawable(R.drawable.ic_close)
        binding.searchBarContainer.setEndIconOnClickListener {
            binding.searchBar.text?.clear()
        }
    }

    private fun hideRecycler() {
        binding.searchResultRecyclerView.visibility = View.GONE
    }

}
