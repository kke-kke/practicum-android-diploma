import org.koin.dsl.module
import ru.practicum.android.diploma.data.dto.repository.SearchVacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository

val repositoryModule = module {
    single<SearchVacanciesRepository> {
        SearchVacanciesRepositoryImpl(
            apiService = get()
        )
    }
}
