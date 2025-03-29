package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.SearchResultBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    private var vacancies: List<Vacancy> = emptyList(),
    private val clickListener: (Vacancy) -> Unit,
) : RecyclerView.Adapter<VacancyViewHolder> () {

    fun updateVacancyList(items: List<Vacancy>) {
        vacancies = items
        notifyDataSetChanged()
    }

    fun addVacanciesList(items: List<Vacancy>) {
        val startPosition = vacancies.size
        val newList = vacancies + items
        vacancies = newList
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyViewHolder(SearchResultBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            clickListener(vacancies[position])
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return vacancies.size
    }
}
