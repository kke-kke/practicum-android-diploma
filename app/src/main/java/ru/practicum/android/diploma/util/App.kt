package ru.practicum.android.diploma.util

import android.app.Application
import ru.practicum.android.diploma.di.databaseModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(databaseModule, networkModule, dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}
