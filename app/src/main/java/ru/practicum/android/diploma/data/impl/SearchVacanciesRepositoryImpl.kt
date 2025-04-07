package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.Response
import ru.practicum.android.diploma.domain.models.VacanciesStateLoad
import ru.practicum.android.diploma.util.Constants

class SearchVacanciesRepositoryImpl(
    private val apiService: ApiService
) : SearchVacanciesRepository {
    override fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int,
        areaId: String?,
        industryId: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<VacanciesStateLoad> {
        return flow {
            emit(VacanciesStateLoad(isLoading = true))
            val response = kotlin.runCatching {
                apiService.searchVacancies(
                    text = text,
                    page = page,
                    perPage = perPage,
                    areaId = areaId,
                    industryId = industryId,
                    salary = salary,
                    onlyWithSalary = onlyWithSalary,
                ).call()
            }.getOrNull()
            emit(
                when (response) {
                    null -> {
                        VacanciesStateLoad(
                            isNetworkError = true,
                        )
                    }

                    is Response.Error -> {
                        val isServerError = response.errorCode >= Constants.START_SERVER_ERROR_CODE

                        VacanciesStateLoad(
                            isServerError = isServerError,
                            isNetworkError = !isServerError,
                            errorMessage = response.errorMessage
                        )
                    }

                    is Response.Success -> VacanciesStateLoad(
                        vacanciesFound = response.data.toDomain()
                    )
                }
            )
        }
    }
}
