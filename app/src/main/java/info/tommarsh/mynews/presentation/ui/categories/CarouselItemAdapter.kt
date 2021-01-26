package info.tommarsh.mynews.presentation.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.util.createDiffItemCallback
import info.tommarsh.mynews.core.util.loadUrl
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.ui.onClickEvent
import info.tommarsh.presentation.databinding.ItemCategoryArticleBinding

class CarouselItemAdapter(private val onClickEvent: onClickEvent) : PagingDataAdapter<ArticleViewModel, CarouselItemViewHolder>(DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val binding =
            ItemCategoryArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselItemViewHolder(binding, onClickEvent)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFFER = createDiffItemCallback<ArticleViewModel> { old, new ->
            old.url == new.url
        }
    }
}

class CarouselItemViewHolder(private val binding: ItemCategoryArticleBinding,
                             private val onClickEvent: onClickEvent) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ArticleViewModel) {
        binding.categoryArticleImage.loadUrl(article.urlToImage)
        binding.categoryArticleName.text = article.title
        binding.categoryArticleUpdated.text = article.publishedAt
        binding.root.setOnClickListener {
            onClickEvent(ClickEvent.Article(article.url, article.title))
        }
    }
}