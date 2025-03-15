package ru.practicum.android.diploma.data.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.dto_models.VacanciesResponse

interface ApiService {
    @Headers(
        "Authorization: Bearer YOUR_TOKEN",
        "HH-User-Agent: Pocket Job (kovaleva.ksenia.e@gmail.com)"
    )
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): Response<VacanciesResponse>
}
