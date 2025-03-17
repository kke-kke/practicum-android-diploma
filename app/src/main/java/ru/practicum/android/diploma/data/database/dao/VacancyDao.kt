package ru.practicum.android.diploma.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.database.entities.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM vacancies")
    fun getAllVacancies(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM vacancies WHERE id = :id")
    fun getVacancyById(id: String): Flow<VacancyEntity?>

    @Query("DELETE FROM vacancies WHERE id = :id")
    suspend fun deleteVacancyById(id: String)
}
