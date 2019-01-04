package info.tommarsh.data

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.data.source.remote.articles.ArticlesRemoteDataStore
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.domain.source.ArticleRepository
import javax.inject.Inject

class ArticleDataRepository
@Inject constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override fun getBreakingNews(source: String): LiveData<List<ArticleModel>> = local.getBreakingNews()

    override fun refreshBreakingNews() {
        val networkItems = remote.getBreakingNews()
        when (networkItems) {
            is Outcome.Success -> local.saveBreakingNews(networkItems.data)
            is Outcome.Error -> errors.setError(networkItems.error)
        }
    }

    override fun getFeed(): LiveData<List<ArticleModel>> = local.getFeed()

    override fun refreshFeed(categories: List<CategoryModel>) {
        val results = mutableListOf<ArticleModel>()
        categories.forEach {
            val networkItems = remote.getArticleForCategory(it.id)
            when (networkItems) {
                is Outcome.Success -> results.addAll(networkItems.data.also { c ->
                    c.forEach { d ->
                        d.category = it.id
                    }
                })
                is Outcome.Error -> errors.setError(networkItems.error)
            }
        }

        local.saveFeed(results)
    }

    override fun searchArticles(query: String) = remote.searchArticles(query)
}