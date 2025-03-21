package ru.practicum.android.diploma.domain.impl

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesResult
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacanciesFound
import ru.practicum.android.diploma.domain.models.VacanciesStateLoad
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchVacanciesInteractorImplTest {

    private val searchVacanciesRepository: SearchVacanciesRepository = mockk()
    private val interactor = SearchVacanciesInteractorImpl(searchVacanciesRepository)

    @Test
    fun `searchVacancies should return Loading when repository returns Loading`() = runTest {
        // Arrange
        val queryMap = mapOf("query" to "Kotlin")
        val vacanciesResponse = VacanciesStateLoad(isLoading = true)
        coEvery { searchVacanciesRepository.searchVacancies(queryMap) } returns flowOf(vacanciesResponse)

        // Act
        val result = interactor.searchVacancies(queryMap).toList()

        // Assert
        assertEquals(listOf(SearchVacanciesResult.Loading), result)
    }

    @Test
    fun `searchVacancies should return Error with network error when repository returns network error`() = runTest {
        // Arrange
        val queryMap = mapOf("query" to "Kotlin")
        val vacanciesResponse = VacanciesStateLoad(isNetworkError = true)
        coEvery { searchVacanciesRepository.searchVacancies(queryMap) } returns flowOf(vacanciesResponse)

        // Act
        val result = interactor.searchVacancies(queryMap).toList()

        // Assert
        assertEquals(listOf(SearchVacanciesResult.Error(isNetworkError = true, isServerError = false)), result)
    }


    @Test
    fun `searchVacancies should return Error with server error when repository returns server error`() = runTest {
        // Arrange
        val queryMap = mapOf("query" to "Kotlin")
        val vacanciesResponse = VacanciesStateLoad(isServerError = true)
        coEvery { searchVacanciesRepository.searchVacancies(queryMap) } returns flowOf(vacanciesResponse)

        // Act
        val result = interactor.searchVacancies(queryMap).toList()

        // Assert
        assertEquals(listOf(SearchVacanciesResult.Error(isNetworkError = false, isServerError = true)), result)
    }

    @Test
    fun `searchVacancies should return Error with nothing found when vacancies list is isNull`() = runTest {
        // Arrange
        val queryMap = mapOf("query" to "Kotlin")
        val vacanciesResponse = VacanciesStateLoad(vacanciesFound = null)
        coEvery { searchVacanciesRepository.searchVacancies(queryMap) } returns flowOf(vacanciesResponse)

        // Act
        val result = interactor.searchVacancies(queryMap).toList()

        // Assert
        assertEquals(listOf(SearchVacanciesResult.Error(isNothingFound = true)), result)
    }
    @Test
    fun `searchVacancies should return Error with nothing found when vacancies list is empty`() = runTest {
        // Arrange
        val queryMap = mapOf("query" to "Kotlin")
        val vacanciesResponse = VacanciesStateLoad(vacanciesFound = VacanciesFound(emptyList(), 0, 0, 1, 10))
        coEvery { searchVacanciesRepository.searchVacancies(queryMap) } returns flowOf(vacanciesResponse)

        // Act
        val result = interactor.searchVacancies(queryMap).toList()

        // Assert
        assertEquals(listOf(SearchVacanciesResult.Error(isNothingFound = true)), result)
    }

    @Test
    fun `searchVacancies should return Success when vacancies list is not empty`() = runTest {
        // Arrange
        val queryMap = mapOf("query" to "Kotlin")
        val vacanciesList = listOf(Vacancy("1", "Kotlin Developer", "https://example.com", null, null, null, null, listOf(), Area("Moscow"), null, Schedule("Full-time"), "2023-07-01"))
        val vacanciesResponse = VacanciesStateLoad(vacanciesFound = VacanciesFound(vacanciesList, 10, 10, 1, 10))
        coEvery { searchVacanciesRepository.searchVacancies(queryMap) } returns flowOf(vacanciesResponse)

        // Act
        val result = interactor.searchVacancies(queryMap).toList()

        // Assert
        assertEquals(listOf(SearchVacanciesResult.Success(vacanciesFound = VacanciesFound(vacanciesList, 10, 10, 1, 10))), result)
    }
}
