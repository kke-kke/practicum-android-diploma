package ru.practicum.android.diploma.data.dto.models

import com.google.gson.annotations.SerializedName

data class AreasDTO(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("parent_id") val parentID: String? = null,
    @SerializedName("areas") val areas: List<AreasDTO>? = null
)
