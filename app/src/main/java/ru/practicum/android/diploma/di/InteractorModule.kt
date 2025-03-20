import org.koin.dsl.module
import ru.practicum.android.diploma.domain.impl.SearchVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesInteractor

val interactorModule = module {
    factory<SearchVacanciesInteractor> {
        SearchVacanciesInteractorImpl(
            searchVacanciesRepository = get()
        )
    }
}
