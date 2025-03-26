package ru.practicum.android.diploma.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.Salary

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val departmentName: String,
    val salary: Salary?,
    val experience: String?,
    val employment: String?,
    val workFormat: String?,
    val description: String?,
    val companyIcon: String?,
    val companyName: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: List<String>,
    @ColumnInfo(name = "alternate_url")
    val alternateUrl: String? = null,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)
