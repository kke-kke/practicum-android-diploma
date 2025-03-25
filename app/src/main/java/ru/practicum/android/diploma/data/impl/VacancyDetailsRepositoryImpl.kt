package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.AppDatabase
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.mappers.toEntity
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.Response
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad
import ru.practicum.android.diploma.util.Constants

class VacancyDetailsRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : VacancyDetailsRepository {

    override fun loadVacancy(vacancyId: String): Flow<VacancyDetailsStateLoad> {
        return flow {
            emit(VacancyDetailsStateLoad(isLoading = true))

            val cachedVacancy = appDatabase.vacancyDao().getVacancyById(vacancyId).firstOrNull()
            if (cachedVacancy != null) {
                emit(
                    VacancyDetailsStateLoad(
                        vacancy = cachedVacancy.toDomain()
                    )
                )
            }

            val response = kotlin.runCatching { apiService.getVacancyDetails(vacancyId).call() }.getOrNull()

            val state = when (response) {
                null -> VacancyDetailsStateLoad(isNetworkError = true)

                is Response.Error -> {
                    when {
                        response.errorCode == Constants.NOT_FOUND_ERROR_CODE -> {
                            appDatabase.vacancyDao().deleteVacancyById(vacancyId)
                            VacancyDetailsStateLoad(isNotFoundError = true)
                        }

                        response.errorCode >= Constants.START_SERVER_ERROR_CODE -> {
                            VacancyDetailsStateLoad(
                                isOtherError = true,
                                isNetworkError = false,
                                errorMessage = response.errorMessage
                            )
                        }

                        else -> VacancyDetailsStateLoad(isNetworkError = true)
                    }
                }

                is Response.Success -> {
                    val vacancyDomain = response.data.toDomain()
                    val oldVacancy = appDatabase.vacancyDao().getVacancyById(vacancyId).firstOrNull()
                    val oldIsFavorite = oldVacancy?.isFavorite ?: false
                    val vacancyEntity = vacancyDomain.toEntity().copy(isFavorite = oldIsFavorite)
                    appDatabase.vacancyDao().insertVacancy(vacancyEntity)
                    VacancyDetailsStateLoad(vacancy = vacancyDomain)
                }
            }
            emit(state)
        }
    }
}
