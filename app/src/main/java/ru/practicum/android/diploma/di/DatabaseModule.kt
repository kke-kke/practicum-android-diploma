import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.util.Constants

//  провайдит AppDatabase, DAO
val databaseModule = module {
    // Database
    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, Constants.DATABASE_NAME
        ).build()
    }
    // Dao
    single {
        get<AppDatabase>().vacancyDao()
    }
}
