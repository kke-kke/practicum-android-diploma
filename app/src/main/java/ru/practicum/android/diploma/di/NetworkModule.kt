import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.util.Constants

val networkModule = module {
    single { provideOkHttpClient() }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<ApiService> {
        NetworkClient(get())
            .getClient()
            .create(ApiService::class.java)
    }
}

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder().apply {
        addInterceptor(logging)
        addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val newRequest: Request =
                originalRequest.newBuilder().header("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}").build()
            chain.proceed(newRequest)
        }
    }.build()
}

