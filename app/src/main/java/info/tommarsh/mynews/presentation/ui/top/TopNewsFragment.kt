package info.tommarsh.mynews.presentation.ui.top

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.core.ui.ListLoadStateAdapter
import info.tommarsh.presentation.R
import info.tommarsh.presentation.databinding.FragmentTopNewsBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopNewsFragment : Fragment() {

    private lateinit var binding: FragmentTopNewsBinding

    private val adapter = TopNewsAdapter()

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
        binding.topNewsRefresher.setOnRefreshListener { adapter.refresh() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
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
            R.id.action_search -> findNavController().navigate(R.id.action_navigation_top_news_to_searchActivity)
        }
        return true
    }

    @OptIn(InternalCoroutinesApi::class)
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
                binding.topNewsRecyclerView.isVisible = loadState is LoadState.NotLoading
                binding.topNewsRefresher.isRefreshing = loadState is LoadState.Loading

                if (loadState is LoadState.NotLoading) {
                    binding.topNewsRecyclerView.scrollToPosition(0)
                }
            }

    }
}