package info.tommarsh.mynews.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.ActivityArticlesBinding

@AndroidEntryPoint
class ArticlesActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener {

    private val binding by lazy { ActivityArticlesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpToolbar()
        setUpNavigation()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.articlesToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller = navHost.navController
        controller.addOnDestinationChangedListener(this)
        setupWithNavController(binding.bottomNavigation, controller)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.articlesToolbar.title = destination.label
    }
}
