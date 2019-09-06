package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.ViewModel

data class PlaylistItemViewModel(
    val videoId: String,
    val title: String,
    val publishedAt: String,
    val thumbnail: String
) : ViewModel