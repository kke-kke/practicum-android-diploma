package ru.practicum.android.diploma.ui.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaViewBinding
import ru.practicum.android.diploma.domain.models.AreaExtended

class CountriesAdapter(
    private val onCountryClick: (AreaExtended) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    private var countries: List<AreaExtended> = emptyList()

    fun submitList(newCountries: List<AreaExtended>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = AreaViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    inner class CountryViewHolder(private val binding: AreaViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: AreaExtended) {
            binding.areaTextView.text = country.name
            binding.root.setOnClickListener { onCountryClick(country) }
        }
    }
}

