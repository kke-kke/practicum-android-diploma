package ru.practicum.android.diploma.data.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.dto_models.AreasResponse
import ru.practicum.android.diploma.data.dto.dto_models.IndustryDTO
import ru.practicum.android.diploma.data.dto.dto_models.VacanciesResponse

interface ApiService {
    @Headers(
        "Authorization: Bearer {ACCESS_TOKEN}",
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("text") query: String
    ): Response<VacanciesResponse>

    @GET("industries")
    fun getIndustries(): Response<List<IndustryDTO>>

    @GET("areas")
    fun getAreas(): Response<AreasResponse>
}
