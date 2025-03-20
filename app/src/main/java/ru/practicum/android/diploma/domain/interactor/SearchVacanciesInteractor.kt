package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow

interface SearchVacanciesInteractor {

    /**
     * Пример использования интерактора поиска вакансий:
     *
     *```kotlin
     * val searchResult = searchVacanciesInteractor.searchVacancies(queryMap)
     *
     * searchResult?.collect { result ->
     *     when (result) {
     *         is SearchVacanciesResult.Loading -> println("Обработка загрузки...")
     *         is SearchVacanciesResult.Error -> {
     *             when {
     *                 result.isServerError -> println("Ошибка сервера")
     *                 result.isNothingFound -> println("Ничего не найдено")
     *                 result.isNetworkError -> println("Ошибка сети")
     *             }
     *         }
     *         is SearchVacanciesResult.Success -> println("Найдено вакансий: ${result.vacanciesFound.vacanciesList}")
     *     }
     * }
     * ```
     */
    fun searchVacancies(queryMap: Map<String, String>): Flow<SearchVacanciesResult>?
}
