package com.lowe.wanandroid.ui.group.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentChildGroupBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore

class GroupChildFragment :
    BaseFragment<GroupChildViewModel, FragmentChildGroupBinding>(R.layout.fragment_child_group) {

    companion object {

        const val KEY_BUNDLE_GROUP_CHILD_ID = "key_bundle_group_child_id"

        fun newInstance(id: Int) =
            with(GroupChildFragment()) {
                arguments = bundleOf(
                    KEY_BUNDLE_GROUP_CHILD_ID to id
                )
                this
            }
    }

    private val authorId by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getInt(
            KEY_BUNDLE_GROUP_CHILD_ID, 0
        ) ?: 0
    }

    private val articlesAdapter = MultiTypeAdapter()

    override val viewModel: GroupChildViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
        onRefresh()
    }

    private fun initObserve() {
        viewModel.apply {
            groupChildArticlesLiveData.observe(
                viewLifecycleOwner,
                this@GroupChildFragment::dispatchToAdapter
            )
        }
    }

    private fun initView() {
        articlesAdapter.register(HomeArticleItemBinder(this::onArticleClick))
        viewBinding.apply {
            with(childList) {
                adapter = articlesAdapter
                layoutManager = LinearLayoutManager(context)
                loadMore(loadFinish = { viewModel.isLoading.not() }) {
                    viewModel.fetchArticles(authorId)
                }
            }
        }
    }

    private fun onRefresh() {
        viewModel.fetchArticles(authorId, true)
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        articlesAdapter.items = result.first
        result.second.dispatchUpdatesTo(articlesAdapter)
    }

    private fun onArticleClick(position: Int, article: Article) {
        WebActivity.loadUrl(requireContext(), article.link)
    }
}
