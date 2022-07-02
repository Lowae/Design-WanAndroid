package com.lowe.wanandroid.ui.message.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.compat.BundleCompat
import com.lowe.wanandroid.databinding.FragmentMessageChildListBinding
import com.lowe.wanandroid.services.model.MsgBean
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.message.MessageChildFragmentAdapter
import com.lowe.wanandroid.ui.message.MessageTabBean
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import com.lowe.wanandroid.utils.showShortToast
import com.lowe.wanandroid.utils.whenError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * 消息子Fragment
 */
@AndroidEntryPoint
class MessageTabChildFragment :
    BaseFragment<MessageTabChildViewModel, FragmentMessageChildListBinding>(R.layout.fragment_message_child_list) {

    companion object {

        fun newInstance(tabBean: MessageTabBean) =
            with(MessageTabChildFragment()) {
                arguments =
                    bundleOf(MessageChildFragmentAdapter.KEY_MESSAGE_CHILD_TAB_PARCELABLE to tabBean)
                this
            }
    }

    private val messagesAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(MessageTabChildItemBinder(this@MessageTabChildFragment::onMsgItemClick))
        }
    private val messageTabBean by lazy(LazyThreadSafetyMode.NONE) {
        BundleCompat.getParcelable(
            arguments,
            MessageChildFragmentAdapter.KEY_MESSAGE_CHILD_TAB_PARCELABLE
        ) ?: MessageTabBean(MessageChildFragmentAdapter.MESSAGE_TAB_NEW)
    }

    override val viewModel: MessageTabChildViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(messageList) {
                adapter = messagesAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            messagesAdapter.loadStateFlow.collectLatest { loadState ->
                loadState.whenError { it.error.message?.showShortToast() }
                updateLoadStates(loadState)
            }
        }
        lifecycleScope.launchWhenCreated {
            if (messageTabBean.title == MessageChildFragmentAdapter.MESSAGE_TAB_NEW) {
                viewModel.getUnreadMsgFlow()
            } else {
                viewModel.getReadiedMsgFlow()
            }.collectLatest(messagesAdapter::submitData)
        }
    }

    private fun onMsgItemClick(position: Int, msgBean: MsgBean) {
        WebActivity.loadUrl(requireContext(), msgBean.fullLink)
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && messagesAdapter.isEmpty()
            loadingProgress.isVisible = messagesAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}