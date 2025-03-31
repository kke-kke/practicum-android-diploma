package ru.practicum.android.diploma.ui.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.IndustryViewModel

class IndustryAdapter(
    private val viewModel: IndustryViewModel,
    private var industries: MutableList<Industry> = mutableListOf(),
    private val clickListener: (Industry) -> Unit,
) : RecyclerView.Adapter<IndustryViewHolder> () {

    fun setItems(items: MutableList<Industry>) {
        industries = items
        notifyDataSetChanged()

        updateSelection()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return IndustryViewHolder(IndustryItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = industries[position]
        val isSelected = industry.id == viewModel.selectedIndustryId.value
        holder.bind(industry, isSelected) { isChecked ->
            if (isChecked && industry.id != viewModel.selectedIndustryId.value) {
                updateSelection(industry.id)
                clickListener(industry)
            }
        }
    }

    private fun updateSelection(newSelectedId: String? = viewModel.selectedIndustryId.value) {
        val previousSelectedId = viewModel.selectedIndustryId.value
        if (previousSelectedId == newSelectedId) return

        val previousPosition = industries.indexOfFirst { it.id == previousSelectedId }
        val newPosition = industries.indexOfFirst { it.id == newSelectedId }

        viewModel.selectIndustry(newSelectedId)

        if (previousPosition != RecyclerView.NO_POSITION) notifyItemChanged(previousPosition)
        if (newPosition != RecyclerView.NO_POSITION) notifyItemChanged(newPosition)
    }

    override fun getItemCount(): Int {
        return industries.size
    }
}
