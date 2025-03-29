package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.database.Converters
import ru.practicum.android.diploma.data.database.storage.FiltersStorage

val dataModule = module {
    single { Gson() }
    single { Converters(get()) }
    single {
        androidContext().getSharedPreferences("filters_prefs", android.content.Context.MODE_PRIVATE)
    }

    single {
        FiltersStorage(get())
    }
}
