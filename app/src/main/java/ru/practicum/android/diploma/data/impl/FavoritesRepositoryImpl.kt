package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.database.AppDatabase
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.mappers.toEntity
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase
) : FavoritesRepository {

    override fun getAllFavorites(): Flow<List<Vacancy>> {
        return appDatabase.vacancyDao().getFavoriteVacancies()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addToFavorites(vacancy: Vacancy) {
        val oldVacancy = appDatabase.vacancyDao().getVacancyById(vacancy.id).firstOrNull()
        if (oldVacancy != null) {
            appDatabase.vacancyDao().updateFavoriteStatus(vacancy.id, true)
        } else {
            appDatabase.vacancyDao().insertVacancy(vacancy.toEntity().copy(isFavorite = true))
        }
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        appDatabase.vacancyDao().updateFavoriteStatus(vacancyId, false)
    }

    override fun isFavoriteFlow(vacancyId: String): Flow<Boolean> {
        return appDatabase.vacancyDao().getVacancyById(vacancyId)
            .map { entity -> entity?.isFavorite == true }
    }
}
