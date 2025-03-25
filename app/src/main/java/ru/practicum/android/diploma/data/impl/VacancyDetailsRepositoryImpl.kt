package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.Response
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetailsStateLoad
import ru.practicum.android.diploma.util.Constants

class VacancyDetailsRepositoryImpl(private val apiService: ApiService) : VacancyDetailsRepository {

    override fun loadVacancy(vacancyId: String): Flow<VacancyDetailsStateLoad> {
        return flow {
            emit(VacancyDetailsStateLoad(isLoading = true))

//            val cachedVacancy = vacancyDao.getVacancyById(vacancyId).firstOrNull()
//            if (cachedVacancy != null) {
//                emit(
//                    VacancyDetailsStateLoad(
//                        vacancy = cachedVacancy.toDomain()
//                    )
//                )
//            }

            val response = kotlin.runCatching { apiService.getVacancyDetails(vacancyId).call() }.getOrNull()

            val state = when (response) {
                null -> VacancyDetailsStateLoad(isNetworkError = true)

                is Response.Error -> {
                    when {
                        response.errorCode == 404 -> {
//                            vacancyDao.deleteVacancyById(vacancyId)
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
                    val vacancy = response.data.toDomain()
//                    vacancyDao.insertVacancy(vacancy.toEntity()) // Сохраняем в БД
                    VacancyDetailsStateLoad(vacancy = vacancy)
                }
            }

            emit(state)
        }
    }

//    override suspend fun deleteVacancy(vacancyId: String) {
//        appDatabase.vacancyDao().deleteVacancyById(vacancyId)
//    }
}
