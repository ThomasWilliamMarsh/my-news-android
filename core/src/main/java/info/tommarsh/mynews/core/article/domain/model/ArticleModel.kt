package info.tommarsh.mynews.core.article.domain.model

import info.tommarsh.mynews.core.model.DomainModel


data class ArticleModel(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceModel,
    val title: String,
    val url: String,
    val urlToImage: String,
    var category: String = "business"
) : DomainModel