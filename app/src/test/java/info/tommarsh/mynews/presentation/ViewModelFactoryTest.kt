package info.tommarsh.mynews.presentation

import androidx.lifecycle.ViewModel
import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.categories.ui.CategoryChoiceViewModel
import info.tommarsh.mynews.core.ViewModelFactory
import info.tommarsh.mynews.presentation.ui.categories.CategoriesViewModel
import info.tommarsh.mynews.presentation.ui.top.TopNewsViewModel
import info.tommarsh.mynews.presentation.ui.videos.VideosViewModel
import info.tommarsh.mynews.search.ui.SearchViewModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import javax.inject.Provider

class ViewModelFactoryTest {

    private lateinit var factory: ViewModelFactory

    private val categoriesViewModel = mock<CategoriesViewModel>()
    private val topNewViewModel = mock<TopNewsViewModel>()
    private val videosViewModel = mock<VideosViewModel>()
    private val categoryChoiceViewModel = mock<CategoryChoiceViewModel>()
    private val searchViewModel = mock<SearchViewModel>()

    private var map: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>> = mutableMapOf(
        CategoriesViewModel::class.java to Provider { categoriesViewModel },
        TopNewsViewModel::class.java to Provider { topNewViewModel },
        VideosViewModel::class.java to Provider { videosViewModel },
        CategoryChoiceViewModel::class.java to Provider { categoryChoiceViewModel },
        SearchViewModel::class.java to Provider { searchViewModel }
    )

    @Before
    fun setUp() {
        factory = ViewModelFactory(map)
    }

    @Test
    fun shouldCreateCategoriesViewModel() {
        val viewModel = factory.create(CategoriesViewModel::class.java)

        assertThat(viewModel, `is`(instanceOf(CategoriesViewModel::class.java)))
    }

    @Test
    fun shouldCreateTopNewsViewModel() {
        val viewModel = factory.create(TopNewsViewModel::class.java)

        assertThat(viewModel, `is`(instanceOf(TopNewsViewModel::class.java)))
    }

    @Test
    fun shouldCreateVideosViewModel() {
        val viewModel = factory.create(VideosViewModel::class.java)

        assertThat(viewModel, `is`(instanceOf(VideosViewModel::class.java)))
    }

    @Test
    fun shouldCreateCategoryChoiceViewModel() {
        val viewModel = factory.create(CategoryChoiceViewModel::class.java)

        assertThat(viewModel, `is`(instanceOf(CategoryChoiceViewModel::class.java)))
    }

    @Test
    fun shouldCreateSearchViewModel() {
        val viewModel = factory.create(SearchViewModel::class.java)

        assertThat(viewModel, `is`(instanceOf(SearchViewModel::class.java)))
    }
}