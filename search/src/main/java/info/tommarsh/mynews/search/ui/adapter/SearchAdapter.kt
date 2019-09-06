package info.tommarsh.mynews.search.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import info.tommarsh.mynews.core.extensions.getDiffUtilItemCallback
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.ui.adapter.viewholder.ArticleViewHolder

class SearchAdapter : ListAdapter<SearchItemViewModel, ArticleViewHolder>(
    callback
) {
    companion object {
        private val callback =
            getDiffUtilItemCallback<SearchItemViewModel> { old, new -> old.url == new.url }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(getItem(position))
}