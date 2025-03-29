package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mappers.toDomainList
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.models.AreaExtended
import ru.practicum.android.diploma.domain.models.Response

class AreasRepositoryImpl(
    private val apiService: ApiService
) : AreasRepository {

    override suspend fun getAllAreas(): Result<List<AreaExtended>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val response = apiService.getAreas().call()
                when (response) {
                    is Response.Success -> response.data.toDomainList()
                    is Response.Error -> {
                        throw IllegalStateException("Ошибка загрузки региона: ${response.errorMessage}")
                    }
                }
            }
        }
    }
}
