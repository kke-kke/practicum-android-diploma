package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {

    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return favoritesRepository.getAllFavorites()
    }

    override fun isFavoriteVacancyFlow(vacancyId: String): Flow<Boolean> {
        return favoritesRepository.isFavoriteFlow(vacancyId)
    }

    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        favoritesRepository.addToFavorites(vacancy)
    }

    override suspend fun removeVacancyFromFavorites(vacancyId: String) {
        favoritesRepository.removeFromFavorites(vacancyId)
    }
}
