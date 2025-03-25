package ru.practicum.android.diploma.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.database.AppDatabase
import ru.practicum.android.diploma.data.database.Converters
import ru.practicum.android.diploma.util.Constants

val databaseModule = module {
    // миграция 1->2 (ALTER TABLE ...)
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL("ALTER TABLE vacancies ADD COLUMN alternate_url TEXT")
        }
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .addTypeConverter(get<Converters>())
            .build()
    }
    single {
        get<AppDatabase>().vacancyDao()
    }
}
