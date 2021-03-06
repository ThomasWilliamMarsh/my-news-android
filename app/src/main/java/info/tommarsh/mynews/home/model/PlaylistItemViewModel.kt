package info.tommarsh.mynews.home.model

import info.tommarsh.mynews.core.model.ViewModel

data class PlaylistItemViewModel(
    val videoId: String,
    val title: String,
    val publishedAt: String,
    val thumbnail: String
) : ViewModel