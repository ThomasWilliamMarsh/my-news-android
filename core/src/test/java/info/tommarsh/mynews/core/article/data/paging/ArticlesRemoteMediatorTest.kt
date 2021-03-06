package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.*
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.paging.PagingLocalDataStore
import info.tommarsh.mynews.paging.SyncTransactionRunner
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediatorTest : UnitTest<ArticlesRemoteMediator>() {

    private val category = fixture<String>()
    private val articleModel = fixture<ArticleModel>()
    private val networkException = NetworkException.ServerException()

    private val remoteSource = mock<ArticlesRemoteDataStore>()
    private val localArticleSource = mock<ArticlesLocalDataStore>()
    private val pageSource = mock<PagingLocalDataStore>()
    private val transactionRunner = SyncTransactionRunner()

    private val config = PagingConfig(pageSize = 20)
    private val pagingState = PagingState<Int, Article>(
        config = config,
        pages = mock(),
        anchorPosition = 1,
        leadingPlaceholderCount = 1
    )

    @Test
    fun `Notify end of pagination`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(1, config.initialLoadSize, category))
            .thenReturn(Resource.Data(emptyList()))

        val actual = sut.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertTrue(actual is RemoteMediator.MediatorResult.Success)
        assertTrue((actual as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `Successful refresh`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(1, config.initialLoadSize, category))
            .thenReturn(Resource.Data(listOf(articleModel)))

        sut.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        verify(remoteSource).getArticleForCategory(1, config.initialLoadSize, category)
        verify(localArticleSource).insertArticles(listOf(articleModel))
        verify(localArticleSource).clearCategory(category)
    }

    @Test
    fun `Failed refresh`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(1, config.initialLoadSize, category))
            .thenReturn(Resource.Error(networkException))

        val result = sut.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        verify(remoteSource).getArticleForCategory(1, config.initialLoadSize, category)
        verify(localArticleSource, never()).clearCategory(category)
        verify(localArticleSource, never()).insertArticles(listOf(articleModel))
    }

    @Test
    fun `Prepend always returns success`() = runBlockingTest {

        val result = sut.load(
            loadType = LoadType.PREPEND,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    fun `Failed append`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(3, config.pageSize, category))
            .thenReturn(Resource.Error(networkException))
        whenever(pageSource.getPageForCategory(category))
            .thenReturn(2)

        val result = sut.load(
            loadType = LoadType.APPEND,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        verify(pageSource).getPageForCategory(category)
        verify(localArticleSource, never()).clearCategory(category)
        verify(remoteSource).getArticleForCategory(3, config.pageSize, category)
        verify(localArticleSource, never()).insertArticles(listOf(articleModel))
    }

    @Test
    fun `Successful append`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(3, config.pageSize, category))
            .thenReturn(Resource.Data(listOf(articleModel)))
        whenever(pageSource.getPageForCategory(category))
            .thenReturn(2)

        val result = sut.load(
            loadType = LoadType.APPEND,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        verify(pageSource).getPageForCategory(category)
        verify(localArticleSource, never()).clearCategory(category)
        verify(remoteSource).getArticleForCategory(3, config.pageSize, category)
        verify(localArticleSource).insertArticles(listOf(articleModel))
    }

    override fun createSut(): ArticlesRemoteMediator {
        return ArticlesRemoteMediator(
            category,
            remoteSource,
            localArticleSource,
            pageSource,
            transactionRunner
        )
    }
}