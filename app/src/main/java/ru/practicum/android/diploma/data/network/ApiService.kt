package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.models.AreasDTO
import ru.practicum.android.diploma.data.dto.models.IndustryDTO
import ru.practicum.android.diploma.data.dto.models.VacancyDTO
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse

interface ApiService {
    @Headers(
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("vacancies")

    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("area") areaId: Int? = null,
        @Query("industry") industryId: Int? = null,
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean = false,
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
