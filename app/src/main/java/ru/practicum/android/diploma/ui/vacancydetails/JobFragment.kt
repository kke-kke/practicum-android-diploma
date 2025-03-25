package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentJobBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsScreenState
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.VacancyUtils.toSalaryString

class JobFragment : BaseFragment<FragmentJobBinding>() {

    private val viewModel by viewModel<VacancyDetailsViewModel>()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJobBinding {
        return FragmentJobBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = arguments?.getString("vacancy")
        if (vacancyId != null) {
            viewModel.loadVacancy(vacancyId, requireContext())
        } else {
            showPlaceholder(getString(R.string.vacancy_not_found))
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.observeScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailsScreenState.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is VacancyDetailsScreenState.Success -> {
                    binding.progressBar.isVisible = false
                    bindVacancy(state.vacancy)
                }

                is VacancyDetailsScreenState.Error -> {
                    binding.progressBar.isVisible = false
//                    showPlaceholder(state.errorMessage)
                }
            }
        }
    }

    private fun bindVacancy(vacancy: Vacancy) {
        with(binding) {
            jobName.text = vacancy.name
            salary.text = vacancy.salary.toSalaryString()
            companyName.text = vacancy.employer?.name
            area.text = vacancy.area.name
            experienceValue.text = vacancy.experience?.name
            workFormat.text = vacancy.schedule?.name
            employmentForm.text = vacancy.employment?.name
            descriptionValue.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT)

            if (vacancy.keySkills.isNotEmpty()) {
                keySkills.text = vacancy.keySkills.joinToString(", ") { it.name }
            } else {
                keySkills.isVisible = false
                // id к заголовку
            }

            Glide.with(binding.companyImage)
                .load(vacancy.employer?.logoUrl)
                .placeholder(R.drawable.logo_placeholder_48)
                .error(R.drawable.logo_placeholder_48)
                .fallback(R.drawable.logo_placeholder_48)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(4)))
                .into(companyImage)
        }
    }

    private fun showPlaceholder(message: String) {
        binding.progressBar.isVisible = false
    }

}
