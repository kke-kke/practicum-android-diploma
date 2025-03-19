package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

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


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
