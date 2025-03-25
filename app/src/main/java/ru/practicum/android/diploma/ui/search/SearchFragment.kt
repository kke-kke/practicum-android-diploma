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

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val adapter = VacancyAdapter(clickListener = { vacancy -> showVacancyDetail(vacancy) })
    private fun showVacancyDetail(vacancy: Vacancy) {
        val bundle = Bundle()
        bundle.putString("vacancy", vacancy.id)
        findNavController().navigate(R.id.jobFragment, bundle)
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
                    isPaginationLoader = false
                } else {
                    setClearIcon()
                    binding.searchPlaceholder.visibility = View.GONE
                    isPaginationLoader = false
                    viewModel.searchVacancies(searchedText = s.toString())
                }
            }

        })

        viewModel.searchScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacanciesScreenState.Content -> showContent(
                    vacancyList = state.vacancyList,
                    foundVacanciesCount = state.foundVacanciesCount,
                    isPaginationLoading = isPaginationLoader,
                )

                is VacanciesScreenState.Loading -> showMainContentLoader()
                is VacanciesScreenState.NetworkError -> showNetworkError()
                is VacanciesScreenState.NothingFound -> showNothingFound()
                is VacanciesScreenState.ServerError -> showServerError()
            }
        }

        binding.searchResultRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.getNextPartOfVacancies()
                        isPaginationLoader = true
                    }
                }
            }
        )


    }

    private fun showServerError() {
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        recyclerViewVisibility()
        vacancyCountVisibility()
        errorMessageVisibility(isShowServerError = true)
    }

    private fun showNetworkError() {
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        recyclerViewVisibility()
        vacancyCountVisibility()
        errorMessageVisibility(isShowNetworkError = true)
    }

    private fun showNothingFound() {
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        recyclerViewVisibility()
        errorMessageVisibility(isShowNothingFound = true)
        vacancyCountVisibility(isShown = true)
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
        foundVacanciesCount: Int,
        isPaginationLoading: Boolean
    ) {
        adapter.updateVacancyList(vacancyList)
        recyclerViewVisibility(isShown = true)
        vacancyCountVisibility(isShown = true, count = foundVacanciesCount)
        progressBarContentVisibility()
        progressBarPaginationVisibility()
        errorMessageVisibility()

        if (isPaginationLoading) {
            binding.searchResultRecyclerView.smoothScrollToPosition(0)
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

//        vacancyCountVisibility(
//            when {
//                isShowNothingFound -> isShowNothingFound
//                isShowNetworkError -> isShowNetworkError
//                isShowServerError -> isShowServerError
//                else -> true
//            }
//        )

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
