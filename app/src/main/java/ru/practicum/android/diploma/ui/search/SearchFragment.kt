package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.state.VacanciesScreenState
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.VacancyUtils
import ru.practicum.android.diploma.util.hideKeyboard
import ru.practicum.android.diploma.util.showCustomSnackBar

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val adapter = VacancyAdapter(clickListener = { vacancy -> showVacancyDetail(vacancy) })
    private fun showVacancyDetail(vacancy: Vacancy) {
        val bundle = Bundle()
        bundle.putString("vacancy", vacancy.id)
        findNavController().navigate(R.id.jobFragment, bundle)
    }

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultRecyclerView.adapter = adapter

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setSearchIcon()
                    binding.searchPlaceholder.visibility = View.VISIBLE
                    recyclerViewVisibility(false)
                    vacancyCountVisibility(false)
                    errorMessageVisibility()
                } else {
                    setClearIcon()
                    binding.searchPlaceholder.visibility = View.GONE
                    viewModel.searchVacancies(searchedText = s.toString())
                }
            }

        })
//        viewModel.searchVacancies("")

        viewModel.searchScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacanciesScreenState.Content -> {
                    adapter.updateVacancyList(state.vacancyList)
                    recyclerViewVisibility(true)
                    binding.vacancyCount.isVisible = true
//                    vacancyCountVisibility(isShown = true, count = state.foundVacanciesCount)
                    progressBarContentVisibility(false)
                    errorMessageVisibility()
                }

                is VacanciesScreenState.Loading -> {
                    progressBarContentVisibility(true)
                    hideKeyboard()
                }

                is VacanciesScreenState.NetworkError -> {
                    recyclerViewVisibility()
                    errorMessageVisibility(
                        isShowNetworkError = true,
                    )
                }

                is VacanciesScreenState.NothingFound -> {
                    recyclerViewVisibility()
                    errorMessageVisibility(
                        isShowNothingFound = true,
                    )
                }

                is VacanciesScreenState.ServerError -> {
                    recyclerViewVisibility()
                    errorMessageVisibility(
                        isShowServerError = true
                    )
                }
            }
        }


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

    private fun recyclerViewVisibility(isShown: Boolean = false) {
        binding.searchResultRecyclerView.isVisible = isShown
    }

    private fun vacancyCountVisibility(isShown: Boolean = false, count: Int = 0) {
        binding.vacancyCount.isVisible = isShown
        if (isShown) {
            binding.vacancyCount.text = if (count > 0) {
                "Найдено ${VacancyUtils.divideIntoDigits(count)} ${
                    resources.getQuantityString(
                        R.plurals.vacancy_count,
                        count,
                        count
                    )
                }"
            } else {
                getString(R.string.no_results)
            }
        }
    }

    private fun progressBarContentVisibility(isShown: Boolean = false) {
        binding.searchPlaceholder.isVisible = false
        binding.progressBarContent.isVisible = isShown
    }

    private fun progressBarPaginationVisibility(isShown: Boolean = false) {
        binding.searchPlaceholder.isVisible = false
        binding.progressBarContent.isVisible = isShown
    }

    private fun errorMessageVisibility(
        isShowNothingFound: Boolean = false,
        isShowNetworkError: Boolean = false,
        isShowServerError: Boolean = false
    ) {
        progressBarContentVisibility(false)
        binding.notFound.isVisible = isShowNothingFound
        binding.internetError.isVisible = isShowNetworkError
        binding.serverError.isVisible = isShowServerError

        vacancyCountVisibility(
            when {
                isShowNothingFound -> true
                isShowNetworkError -> false
                isShowServerError -> false
                else -> false
            }
        )

//        val errorMessage = when {
//            isShowNothingFound -> getString(R.string.no_results)
//            isShowNetworkError -> getString(R.string.no_internet)
//            isShowServerError -> getString(R.string.server_error)
//            else -> ""
//        }
//
//        if (errorMessage.isNotEmpty()) {
//            showCustomSnackBar(errorMessage, binding.root, requireContext())
//        }
    }


}
