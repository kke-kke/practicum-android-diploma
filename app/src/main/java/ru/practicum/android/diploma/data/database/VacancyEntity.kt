package ru.practicum.android.diploma.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val departmentName: String,
    val salary: String?,
    val experience: String?,
    val employment: String?,
    val workFormat: String?,
    val description: String?,
    val companyIcon: String?,
    val companyName: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: List<String>
)
