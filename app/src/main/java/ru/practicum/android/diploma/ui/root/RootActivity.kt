package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Retrofit
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.call
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.domain.models.Response

class RootActivity : AppCompatActivity() {
    private var _binding: ActivityRootBinding? = null
    private val rootBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(rootBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        rootBinding.bottomNavigationView.setupWithNavController(navController)

        val setOfVisibleFragments = arrayOf(
            R.id.searchFragment,
            R.id.favoritesFragment,
            R.id.teamFragment
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            rootBinding.bottomNavigationView.isVisible = destination.id in setOfVisibleFragments
            rootBinding.navBarDivider.isVisible = destination.id in setOfVisibleFragments
        }

        CoroutineScope(Dispatchers.IO).launch {
            val apiService by inject<ApiService>()
            val result = apiService.getVacancyDetails("118407927")
            when(result.call()){
                is Response.Error -> println(result.errorBody())
                is Response.Success<*> -> println(result.body())
            }
        }



        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun networkRequestExample(accessToken: String) {
    }

}
