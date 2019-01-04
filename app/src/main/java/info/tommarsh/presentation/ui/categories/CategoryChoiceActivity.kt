package info.tommarsh.presentation.ui.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.tommarsh.presentation.NewsApp.Companion.graph
import info.tommarsh.presentation.R
import info.tommarsh.presentation.ViewModelFactory
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.ui.categories.adapter.CategoryChoiceAdapter
import kotlinx.android.synthetic.main.activity_category_choice.*
import javax.inject.Inject

class CategoryChoiceActivity : AppCompatActivity() {

    @Inject
    protected lateinit var factory: ViewModelFactory

    private val viewModel: CategoryChoiceViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CategoryChoiceViewModel::class.java)
    }

    private val adapter = CategoryChoiceAdapter(::onCategorySelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_choice)
        graph(this).inject(this)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpUi() {
        setSupportActionBar(activity_choice_toolbar)
        activity_choice_recycler_view.adapter = adapter
        activity_choice_recycler_view.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }

    private fun setUpViewModel() {
        viewModel.getCategories().observe(this, Observer(::onCategories))
    }

    private fun onCategories(categories: List<CategoryViewModel>) {
        adapter.submitList(categories)
    }

    private fun onCategorySelected(category: CategoryViewModel) {
        category.selected = !category.selected
        viewModel.updateCategory(category)
    }
}