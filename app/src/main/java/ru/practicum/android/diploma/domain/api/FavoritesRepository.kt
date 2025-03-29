package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<Vacancy>>
    suspend fun addToFavorites(vacancy: Vacancy)
    suspend fun removeFromFavorites(vacancyId: String)
    fun isFavoriteFlow(vacancyId: String): Flow<Boolean>
}
