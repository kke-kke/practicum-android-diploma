package ru.practicum.android.diploma.ui.filters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(private val binding: IndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Industry, isSelected: Boolean, onCheckedChange: (Boolean) -> Unit) {
        with(binding) {
            industryName.text = model.name

            radioButton.setOnCheckedChangeListener(null)
            radioButton.isChecked = isSelected
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked != isSelected) {
                    onCheckedChange(isChecked)
                }
            }
        }
    }
}
