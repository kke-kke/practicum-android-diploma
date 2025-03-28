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
import androidx.recyclerview.widget.RecyclerView
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
        findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
    }

    private val viewModel by viewModel<SearchViewModel>()

    private var isPaginationLoader: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultRecyclerView.adapter = adapter

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setSearchIcon()
                    binding.searchPlaceholder.visibility = View.VISIBLE
                    recyclerViewVisibility(false)
                    vacancyCountVisibility(false)
                    errorMessageVisibility()
                    isPaginationLoader = false
                } else {
                    setClearIcon()
                    binding.searchPlaceholder.visibility = View.GONE
                    isPaginationLoader = false
                    adapter.updateVacancyList(emptyList())
                    errorMessageVisibility()
                    vacancyCountVisibility()
                    viewModel.searchVacancies(searchedText = s.toString())
                }
            }
        })

        binding.searchResultRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val layoutManager = binding.searchResultRecyclerView.layoutManager as LinearLayoutManager
                    val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                    val itemsCount = adapter.itemCount

                    if (pos >= itemsCount - 1) {
                        viewModel.getNextPartOfVacancies()
                        isPaginationLoader = true
                    }
                }
            }
        })

        viewModel.searchScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacanciesScreenState.Content -> showContent(
                    vacancyList = state.vacancyList,
                    foundVacanciesCount = state.foundVacanciesCount
                )

                is VacanciesScreenState.Loading -> showMainContentLoader()
                is VacanciesScreenState.NetworkError -> showNetworkError()
                is VacanciesScreenState.NothingFound -> showNothingFound()
                is VacanciesScreenState.ServerError -> showServerError()
            }
        }

        binding.filterButton.setOnClickListener{
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }

    }

    private fun showServerError() {
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        if (isPaginationLoader) {
            errorToastVisibility()
        } else {
            recyclerViewVisibility()
            vacancyCountVisibility()
            errorMessageVisibility(isShowServerError = true)
        }
    }

    private fun showNetworkError() {
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        if (isPaginationLoader) {
            errorToastVisibility(isShowNetworkError = true)
        } else {
            vacancyCountVisibility()
            recyclerViewVisibility()
            errorMessageVisibility(isShowNetworkError = true)
        }
    }

    private fun showNothingFound() {
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        if (isPaginationLoader) {
            errorToastVisibility()
        } else {
            recyclerViewVisibility()
            errorMessageVisibility(isShowNothingFound = true)
            vacancyCountVisibility(isShown = true)
        }
    }

    private fun showMainContentLoader() {
        if (!isPaginationLoader) {
            recyclerViewVisibility()
            progressBarContentVisibility(isShown = true)
            vacancyCountVisibility()
        } else {
            progressBarPaginationVisibility(isShown = true)
        }

        errorMessageVisibility()
        hideKeyboard()
    }

    private fun showContent(
        vacancyList: List<Vacancy>,
        foundVacanciesCount: Int
    ) {
        adapter.updateVacancyList(vacancyList)
        recyclerViewVisibility(isShown = true)
        vacancyCountVisibility(isShown = true, count = foundVacanciesCount)
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        errorMessageVisibility()
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
        binding.progressBarPagination.isVisible = isShown
    }

    private fun errorMessageVisibility(
        isShowNothingFound: Boolean = false,
        isShowNetworkError: Boolean = false,
        isShowServerError: Boolean = false
    ) {
        binding.notFound.isVisible = isShowNothingFound
        binding.internetError.isVisible = isShowNetworkError
        binding.serverError.isVisible = isShowServerError
    }

    private fun errorToastVisibility(
        isShowNetworkError: Boolean = false,
    ) {
        val errorMessage = when {
            isShowNetworkError -> getString(R.string.toast_no_internet)
            else -> getString(R.string.toast_error)
        }
        showCustomSnackBar(errorMessage, binding.root, requireContext())
    }
}
