package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.models.AreasDTO
import ru.practicum.android.diploma.data.dto.models.IndustryDTO
import ru.practicum.android.diploma.data.dto.models.VacancyDTO
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.domain.models.Response

interface ApiService {
    @Headers(
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("vacancies")

    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<VacanciesResponse>

    @Headers(
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") vacancyID: String): Response<VacancyDTO>

    @Headers(
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("industries")
    suspend fun getIndustries(): Response<List<IndustryDTO>>

    @Headers(
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("areas")
    suspend fun getAreas(): Response<List<AreasDTO>>
}
