package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.model.ViewModel

data class ArticleViewModel(
    val author: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val content: String,
    val url: String,
    val urlToImage: String,
    val category: String
) : ViewModel {

    override fun contentsTheSame(other: ViewModel): Boolean {
        val otherArticle = other as ArticleViewModel
        return url == otherArticle.url && category == otherArticle.category
    }
}