package ru.practicum.android.diploma.ui.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaViewBinding
import ru.practicum.android.diploma.domain.models.AreaExtended
import ru.practicum.android.diploma.presentation.filters.RegionViewModel

class RegionAdapter(
    private val viewModel: RegionViewModel,
    private var regions: MutableList<AreaExtended> = mutableListOf(),
    private val clickListener: (AreaExtended) -> Unit
) : RecyclerView.Adapter<RegionViewHolder>() {

    fun setItems(items: List<AreaExtended>) {
        regions = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = AreaViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RegionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regions[position]
        holder.bind(region)
        holder.itemView.setOnClickListener {
            viewModel.selectRegion(region)
            clickListener(region)
        }
    }

    override fun getItemCount(): Int = regions.size
}

class RegionViewHolder(private val binding: AreaViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(region: AreaExtended) {
        binding.areaTextView.text = region.name
    }
}
