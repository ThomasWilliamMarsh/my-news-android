package info.tommarsh.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.NetworkException
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.MockModelProvider.articleModel
import info.tommarsh.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.presentation.model.MockModelProvider.noInternet
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val errorsLiveData = ErrorLiveData()
    private val articlesRepository = mock<ArticleRepository> {
        onBlocking { searchArticles("1234") }.thenReturn(Outcome.Error(noInternet))
        on { errors }.thenReturn(errorsLiveData)
    }
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(articleModel) }.thenReturn(articleViewModel)
    }
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val searchViewModel = SearchViewModel(articlesRepository, mapper, dispatcherProvider)
    private val articlesObserver = mock<Observer<List<ArticleViewModel>>>()
    private val errorObserver = mock<Observer<NetworkException>>()


    @Before
    fun `Set up`() {
        searchViewModel.getErrors().observeForever(errorObserver)
        searchViewModel.articles.observeForever(articlesObserver)
    }

    @After
    fun `Tear down`() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        searchViewModel.getErrors().removeObserver(errorObserver)
        searchViewModel.articles.removeObserver(articlesObserver)
    }

    @Test
    fun `Get errors`() {

        searchViewModel.getErrors()

        verify(articlesRepository, times(2)).errors
    }

    @Test
    fun `get search results`() = testCoroutineDispatcher.runBlockingTest {
        whenever(articlesRepository.searchArticles("1234")).thenReturn(
            Outcome.Success(
                listOf(
                    articleModel,
                    articleModel
                )
            )
        )

        searchViewModel.searchArticles("1234")

        verify(articlesRepository).searchArticles("1234")
        verify(articlesObserver).onChanged(listOf(articleViewModel, articleViewModel))
    }

    @Test
    fun `show error when failing to get search results`() = testCoroutineDispatcher.runBlockingTest {

        searchViewModel.searchArticles("1234")

        verify(errorObserver).onChanged(noInternet)
    }
}