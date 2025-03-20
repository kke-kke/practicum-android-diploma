import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.util.Constants

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
    single {
        get<AppDatabase>().vacancyDao()
    }
}
