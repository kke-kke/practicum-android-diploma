package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mappers.toDomainList
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Response

class IndustryRepositoryImpl(
    private val apiService: ApiService
) : IndustryRepository {

    override suspend fun getIndustries(): Result<List<Industry>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val response = apiService.getIndustries().call()
                when (response) {
                    is Response.Success -> {
                        val data = response.data.toDomainList()
                        data
                    }
                    is Response.Error -> {
                        throw IllegalStateException("Ошибка загрузки списка индустрий: ${response.errorMessage}")
                    }
                }
            }
        }
    }
}
