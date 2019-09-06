package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.repository.model.local.Article
import javax.inject.Inject

class ArticleDomainToDataMapper
@Inject constructor(
    private val sourceMapper: SourceDomainToDataMapper
) : Mapper<List<ArticleModel>, List<Article>> {

    override fun map(from: List<ArticleModel>) = from.map {
        Article(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            source = sourceMapper.map(it.source),
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
            category = it.category
        )
    }

}