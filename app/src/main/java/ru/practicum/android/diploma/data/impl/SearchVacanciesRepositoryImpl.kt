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
import ru.practicum.android.diploma.domain.api.FilterRepository // NEW CODE

class SearchVacanciesRepositoryImpl(
    private val apiService: ApiService,
    private val filterRepository: FilterRepository
) : SearchVacanciesRepository {

    override fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int
    ): Flow<VacanciesStateLoad> {
        return flow {
            emit(VacanciesStateLoad(isLoading = true))
            val filters = filterRepository.getFilterParameters()

            val response = kotlin.runCatching {
                apiService.searchVacancies(
                    text = text,
                    page = page,
                    perPage = perPage,
                    salaryFrom = filters.salaryFrom,
                    onlyWithSalary = if (filters.excludeNoSalary) true else null,
                    industryId = filters.industryId,
                    areaId = decideAreaId(filters)
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

    private fun decideAreaId(filters: ru.practicum.android.diploma.domain.models.FilterParameters): String? {
        return when {
            !filters.regionId.isNullOrBlank() -> filters.regionId
            !filters.countryId.isNullOrBlank() -> filters.countryId
            else -> null
        }
    }
}
