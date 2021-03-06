package info.tommarsh.mynews.home.ui

import android.content.Context
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.ui.categories.CategoriesFragment
import info.tommarsh.mynews.home.ui.top.TopNewsFragment
import info.tommarsh.mynews.home.ui.videos.VideosFragment

class HomeFragmentMenuNavigator(
    private val fragmentManager: FragmentManager,
    private val context: Context,
    private val onDestinationChanged: (label: String) -> Unit
) : NavigationBarView.OnItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val data = when (item.itemId) {
            R.id.navigation_top_news -> context.getString(R.string.top_news) to TopNewsFragment()
            R.id.navigation_my_news -> context.getString(R.string.my_news) to CategoriesFragment()
            R.id.navigation_videos -> context.getString(R.string.videos) to VideosFragment()
            else -> return false
        }

        onDestinationChanged(data.first)

        fragmentManager.commit {
            replace(R.id.home_container, data.second)
        }
        return true
    }
}