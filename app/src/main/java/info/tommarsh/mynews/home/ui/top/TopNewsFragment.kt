package info.tommarsh.mynews.home.ui.top

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.navigator.ClickEvent
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.mynews.home.R
import info.tommarsh.mynews.home.databinding.FragmentTopNewsBinding
import info.tommarsh.mynews.home.ui.consumeClick
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopNewsFragment : Fragment() {

    private lateinit var binding: FragmentTopNewsBinding

    private val adapter = TopNewsAdapter { event: ClickEvent ->
        parentFragment?.consumeClick(event)
    }

    private val viewModel by viewModels<TopNewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topNewsRecyclerView.adapter = setUpAdapter()
        binding.topNewsRecyclerView.itemAnimator = null
        binding.topNewsRefresher.setOnRefreshListener { adapter.refresh() }
        lifecycleScope.launchWhenResumed {
            viewModel.articles.collectLatest { data ->
                adapter.submitData(data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> parentFragment?.consumeClick(ClickEvent.Search)
        }
        return true
    }

    private fun setUpAdapter(): ConcatAdapter {
        return adapter.withLoadStateFooter(
            footer = ListLoadStateAdapter { adapter.retry() }
        ).also { setUpLoadStateListener() }
    }

    private fun setUpLoadStateListener() = lifecycleScope.launch {
        adapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .map { it.refresh }
            .collect { loadState ->
                binding.topNewsRefresher.isRefreshing = loadState is LoadState.Loading

                if (loadState is LoadState.NotLoading) {
                    binding.topNewsRecyclerView.scrollToPosition(0)
                }
            }
    }
}