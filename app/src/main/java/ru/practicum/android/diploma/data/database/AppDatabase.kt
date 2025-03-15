import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.database.VacancyDao
import ru.practicum.android.diploma.data.database.VacancyEntity

@Database(
    entities = [VacancyEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
