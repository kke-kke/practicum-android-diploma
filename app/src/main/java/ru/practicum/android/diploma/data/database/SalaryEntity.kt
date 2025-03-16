package ru.practicum.android.diploma.data.database

import androidx.room.ColumnInfo

data class SalaryEntity(
    @ColumnInfo(name = "salary_from")
    val from: Int?,
    @ColumnInfo(name = "salary_to")
    val to: Int?,
    @ColumnInfo(name = "salary_currency")
    val currency: String?,
    @ColumnInfo(name = "salary_gross")
    val gross: Boolean?
)
