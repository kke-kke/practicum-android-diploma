package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {
    fun getFavoriteVacancies(): Flow<List<Vacancy>>
    fun isFavoriteVacancyFlow(vacancyId: String): Flow<Boolean>
    suspend fun addVacancyToFavorites(vacancy: Vacancy)
    suspend fun removeVacancyFromFavorites(vacancyId: String)
}
