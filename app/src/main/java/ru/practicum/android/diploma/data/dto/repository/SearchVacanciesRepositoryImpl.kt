package ru.practicum.android.diploma.data.dto.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.Response
import ru.practicum.android.diploma.domain.models.VacanciesStateLoad

class SearchVacanciesRepositoryImpl(
    private val apiService: ApiService
) : SearchVacanciesRepository {
    override fun searchVacancies(queryMap: Map<String, String>): Flow<VacanciesStateLoad>? {
        return flow {
            emit(VacanciesStateLoad(isLoading = true))
            val response = apiService.searchVacancies(queryMap).call()
            emit(
                when (response) {
                    is Response.Error -> VacanciesStateLoad(
                        isError = true
                    )

                    is Response.Success -> VacanciesStateLoad(
                        vacancies = response.data.toDomain()
                    )
                }
            )
        }
    }
}
