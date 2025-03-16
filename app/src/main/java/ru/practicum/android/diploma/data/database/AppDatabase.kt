import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.database.Converters
import ru.practicum.android.diploma.data.database.VacancyDao
import ru.practicum.android.diploma.data.database.VacancyEntity

@Database(
    entities = [VacancyEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
