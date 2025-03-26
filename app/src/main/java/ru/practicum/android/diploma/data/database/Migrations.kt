package ru.practicum.android.diploma.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

private const val MIGRATION_VERSION_1 = 1
private const val MIGRATION_VERSION_2 = 2
private const val MIGRATION_VERSION_3 = 3
private const val MIGRATION_VERSION_4 = 4

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
    val MIGRATION_3_4 = object : Migration(MIGRATION_VERSION_3, MIGRATION_VERSION_4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE vacancies_new (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                departmentName TEXT NOT NULL,
                salary TEXT, 
                experience TEXT,
                employment TEXT,
                schedule TEXT,
                description TEXT,
                companyIcon TEXT,
                companyName TEXT,
                key_skills TEXT NOT NULL,
                alternate_url TEXT,
                isFavorite INTEGER NOT NULL DEFAULT 0
            )
            """.trimIndent()
            )

            database.execSQL(
                """
            INSERT INTO vacancies_new (id, name, departmentName, salary, experience, employment, schedule, 
                                      description, companyIcon, companyName, key_skills, alternate_url, isFavorite)
            SELECT id, name, departmentName, salary, experience, employment, workFormat, 
                   description, companyIcon, companyName, key_skills, alternate_url, isFavorite 
            FROM vacancies
            """.trimIndent()
            )

            database.execSQL("DROP TABLE vacancies")

            database.execSQL("ALTER TABLE vacancies_new RENAME TO vacancies")
        }
    }
}
