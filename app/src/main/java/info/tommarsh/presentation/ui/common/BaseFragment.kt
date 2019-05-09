package info.tommarsh.presentation.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import info.tommarsh.presentation.NewsApp.Companion.applicationGraph
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ViewModelFactory
import info.tommarsh.presentation.ui.categories.CategoryChoiceActivity
import info.tommarsh.presentation.ui.search.SearchActivity
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    protected lateinit var factory: ViewModelFactory

    val fragmentGraph by lazy {
        applicationGraph(context!!)
            .activityComponent
            .create(context as AppCompatActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> startActivity(Intent(context, SearchActivity::class.java))
            R.id.action_edit -> startActivity(Intent(context, CategoryChoiceActivity::class.java))
        }
        return true
    }
}