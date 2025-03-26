package ru.practicum.android.diploma.ui.vacancydetails

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsScreenState
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.BaseFragment
import ru.practicum.android.diploma.util.VacancyUtils.toSalaryString

class VacancyFragment : BaseFragment<FragmentVacancyBinding>() {

    private val viewModel by viewModel<VacancyDetailsViewModel>()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = arguments?.getString("vacancy")
        if (vacancyId != null) {
            viewModel.loadVacancy(vacancyId)
        } else {
            showPlaceholder(VacancyDetailsScreenState.Error.NotFoundError)
        }

        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.vacancyToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {
        viewModel.observeScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailsScreenState.Loading -> {
                    showLoading()
                }

                is VacancyDetailsScreenState.Success -> {
                    bindVacancy(state.vacancy)
                    showContent()
                }

                is VacancyDetailsScreenState.Error -> {
                    showPlaceholder(state)
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

            if (!vacancy.keySkills.isNullOrEmpty()) {
                keySkills.text = vacancy.keySkills.joinToString(", ") { it.name }
            } else {
                keySkillsTitle.post {
                    keySkillsTitle.visibility = View.GONE
                }
                keySkills.post {
                    keySkills.visibility = View.GONE
                }
            }

            Glide.with(binding.companyImage)
                .load(vacancy.employer?.logoUrl)
                .placeholder(R.drawable.logo_placeholder_48)
                .error(R.drawable.logo_placeholder_48)
                .fallback(R.drawable.logo_placeholder_48)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(R.dimen.dimen_4)))
                .into(companyImage)
            val shareButton = vacancyToolbar.findViewById<ImageButton>(R.id.sharingButton)
            shareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, vacancy.vacancyUrl)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_vacancy)))
            }
        }
    }

    private fun showPlaceholder(error: VacancyDetailsScreenState.Error) {
        with(binding) {
            progressBar.isVisible = false
            hideContent()

            when (error) {
                is VacancyDetailsScreenState.Error.NotFoundError -> {
                    notFound.isVisible = true
                    internetError.isVisible = false
                    serverError.isVisible = false
                }
                is VacancyDetailsScreenState.Error.NoInternetError -> {
                    notFound.isVisible = false
                    internetError.isVisible = true
                    serverError.isVisible = false
                }
                is VacancyDetailsScreenState.Error.OtherError -> {
                    notFound.isVisible = false
                    internetError.isVisible = false
                    serverError.isVisible = true
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            hideContent()
            notFound.isVisible = false
            internetError.isVisible = false
            serverError.isVisible = false
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isVisible = false

            jobName.isVisible = true
            salary.isVisible = true
            companyLinearLayout.isVisible = true
            experienceTitle.isVisible = true
            experienceValue.isVisible = true
            workAndEmploymentLayout.isVisible = true
            descriptionTitle.isVisible = true
            descriptionValue.isVisible = true
            keySkillsTitle.isVisible = true
            keySkills.isVisible = true
        }
    }

    private fun hideContent() {
        with(binding) {
            jobName.isVisible = false
            salary.isVisible = false
            companyLinearLayout.isVisible = false
            experienceTitle.isVisible = false
            experienceValue.isVisible = false
            workAndEmploymentLayout.isVisible = false
            descriptionTitle.isVisible = false
            descriptionValue.isVisible = false
            keySkillsTitle.isVisible = false
            keySkills.isVisible = false
        }
    }

}
