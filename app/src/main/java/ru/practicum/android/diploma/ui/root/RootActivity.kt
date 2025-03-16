package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.domain.models.Response
import ru.practicum.android.diploma.util.Constants

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        CoroutineScope(Dispatchers.IO).launch {
            //println(
                //kotlin.runCatching {
                    val result = NetworkClient()
                        .getClient(baseUrl = Constants.BASE_URL)
                        .create(ApiService::class.java)
                        .getIndustries()
                        .call()
            when(result){
                is Response.Error -> println(result.errorCode)
                is Response.Success -> println(
                    result.data
                )
            }


                //}
            //)

        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
