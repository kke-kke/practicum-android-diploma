package ru.practicum.android.diploma.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

private const val MIGRATION_VERSION_1 = 1
private const val MIGRATION_VERSION_2 = 2
private const val MIGRATION_VERSION_3 = 3

object Migrations {
    val MIGRATION_1_2 = object : Migration(MIGRATION_VERSION_1, MIGRATION_VERSION_2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE vacancies ADD COLUMN alternate_url TEXT")
        }
    }
    val MIGRATION_2_3 = object : Migration(MIGRATION_VERSION_2, MIGRATION_VERSION_3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE vacancies ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0"
            )
        }
    }
}
