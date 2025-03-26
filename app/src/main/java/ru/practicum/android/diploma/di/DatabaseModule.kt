package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.database.AppDatabase
import ru.practicum.android.diploma.data.database.Converters
import ru.practicum.android.diploma.data.database.Migrations
import ru.practicum.android.diploma.util.Constants

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .addMigrations(Migrations.MIGRATION_1_2)
            .addMigrations(Migrations.MIGRATION_2_3)
            .addMigrations(Migrations.MIGRATION_3_4)
            .addTypeConverter(get<Converters>())
            .build()
    }
    single {
        get<AppDatabase>().vacancyDao()
    }
}
