package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.SearchResultBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.VacancyUtils

class VacancyViewHolder(private val binding: SearchResultBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Vacancy) {
        binding.vacancyName.text = model.name
        binding.vacancyEmployer.text = model.employer?.name ?: "Информация отсутствует"

        binding.vacancySalary.text = model.salary?.let { salary ->
            VacancyUtils.getVacancySalary(salary.from, salary.to) + " " + VacancyUtils.getCurrencySymbol(salary.currency)
        } ?: "Зарплата не указана"

        val logoUrl: String? = model.employer?.logoUrl
        Glide.with(itemView.context)
            .load(logoUrl)
            .placeholder(R.drawable.logo_placeholder_48)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.button_corner_radius)))
            .into(binding.vacancyLogo)

    }
}

