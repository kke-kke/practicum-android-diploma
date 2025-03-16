package ru.practicum.android.diploma.util

import android.app.Application
import databaseModule
import interactorModule
import networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import repositoryModule
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
