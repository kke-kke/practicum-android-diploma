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
import ru.practicum.android.diploma.domain.models.*
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.VacancyUtils

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val adapter = VacancyAdapter { vacancy -> showVacancyDetail(vacancy) }

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultRecyclerView.adapter = adapter

        // Пример: при нажатии на filterButton для теста создаем Vacancy и переходим на экран деталей
        binding.filterButton.setOnClickListener {
            // NEW CODE: создаем тестовую вакансию
            val testVacancy = Vacancy(
                id = "118445872",
                name = "Java-разработчик (стажер/intern)",
                vacancyUrl = "https://api.hh.ru/vacancies/118445872?host=hh.ru",
                salary = Salary(null, null, null),
                address = Address("Some address"),
                employer = Employer(
                    name = "Some Employer",
                    logoUrl = "https://img.hhcdn.ru/employer-logo-original/673483.jpg"
                ),
                description = "Заинтересованы в студентах IT-специальностей..." +
                    " Опыт написания кода на <b>Java</b> (учебный проект)",
                keySkills = listOf(KeySkill("skill1"), KeySkill("skill2")),
                area = Area("Москва"),
                experience = Experience("Более 6 лет"),
                schedule = Schedule("Вахтовый метод"),
                employment = Employment("Проектная работа"),
                publishedAt = "123"
            )

            // Переходим в детали вакансии (showVacancyDetail() - ваш метод)
            showVacancyDetail(testVacancy)
        }

        // Пример обычного TextWatcher, который у вас уже был
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    setSearchIcon()
                    binding.searchPlaceholder.visibility = View.VISIBLE
                    recyclerViewVisibility(false)
                    vacancyCountVisibility(false)
                } else {
                    setClearIcon()
                    binding.searchPlaceholder.visibility = View.GONE
                }
            }
        })
    }

    // Здесь можно передать vacancyId через Bundle.
    // Или, если хотите, сразу передавать весь объект Vacancy серийлизованно.
    private fun showVacancyDetail(vacancy: Vacancy) {
        val bundle = Bundle()
        bundle.putString("vacancy", vacancy.id)
        findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
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
                "Найдено ${VacancyUtils.divideIntoDigits(count)} " +
                    resources.getQuantityString(R.plurals.vacancy_count, count, count)
            } else {
                getString(R.string.no_results)
            }
        }
    }
}
