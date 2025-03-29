package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import ru.practicum.android.diploma.data.FiltersStorage
import ru.practicum.android.diploma.data.SharedFiltersStorage
import ru.practicum.android.diploma.data.database.Converters

val dataModule = module {
    single { Gson() }
    single { Converters(get()) }

    single<FiltersStorage> {
        SharedFiltersStorage(
            context = get(),
            gson = get()
        )
    }
}
