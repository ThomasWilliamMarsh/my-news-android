package marsh.tommarsh.search.model

import info.tommarsh.core.NetworkException
import info.tommarsh.core.model.*

object MockModelProvider {

    val fakeIsoTime = "2019-01-01T11:00:00+0000"

    //region article
    val sourceModel = SourceModel("id", "name")
    val articleModel = ArticleModel(
        "author", "content", "description", fakeIsoTime, sourceModel, "title",
        "url", "imageUrl", "top-news"
    )
    val articleViewModel =
        SearchItemViewModel("author", "description", "1 hour ago", "title", "content", "url", "imageUrl", "top-news")

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}