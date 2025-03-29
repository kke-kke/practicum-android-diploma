package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesResult

class SearchVacanciesInteractorImpl(
    private val searchVacanciesRepository: SearchVacanciesRepository
) : SearchVacanciesInteractor {
    override fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int,
        areaId: Int?,
        industryId: Int?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<SearchVacanciesResult> {
        val result = searchVacanciesRepository.searchVacancies(
            text = text,
            page = page,
            perPage = perPage,
            areaId = areaId,
            industryId = industryId,
            salary = salary,
            onlyWithSalary = onlyWithSalary,
        ).map { vacancies ->
            when {
                vacancies.isLoading -> SearchVacanciesResult.Loading
                vacancies.isNetworkError -> SearchVacanciesResult.NetworkError(vacancies.errorMessage ?: "")
                vacancies.isServerError -> SearchVacanciesResult.ServerError(vacancies.errorMessage ?: "")

                vacancies.vacanciesFound != null
                    && vacancies.vacanciesFound.vacanciesList.isNotEmpty() ->
                    SearchVacanciesResult.Success(vacanciesFound = vacancies.vacanciesFound)

                else -> SearchVacanciesResult.NothingFound
            }
        }
        return result
    }
}
