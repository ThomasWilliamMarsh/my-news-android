package info.tommarsh.mynews.presentation.ui.videos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.util.snack
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import info.tommarsh.mynews.presentation.ui.ArticleFragment
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentVideosBinding

@AndroidEntryPoint
class VideosFragment : ArticleFragment() {

    private lateinit var binding: FragmentVideosBinding

    private val adapter = VideosAdapter()

    private val viewModel by viewModels<VideosViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.videosRecyclerView.adapter = adapter
        binding.videosRecyclerView.layoutManager = setLayoutManager()
        binding.refreshVideo.setOnRefreshListener {
            binding.refreshVideo.isRefreshing = true
            viewModel.refreshVideos()
        }
        binding.refreshVideo.isRefreshing = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.videos.observe(viewLifecycleOwner, Observer(::onVideos))
        viewModel.errors.observe(viewLifecycleOwner, Observer(::onError))

        viewModel.refreshVideos()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.videos_menu, menu)
    }

    private fun onVideos(videos: List<PlaylistItemViewModel>) {
        binding.refreshVideo.isRefreshing = false
        adapter.items = videos
    }

    private fun onError(error: NetworkException) {
        binding.refreshVideo.isRefreshing = false
        binding.refreshVideo.snack(error.localizedMessage)
    }

    private fun setLayoutManager() = GridLayoutManager(context, 2)
}