package com.lowe.wanandroid.ui.message.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentMessageChildListBinding
import com.lowe.wanandroid.services.model.MsgBean
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.message.MessageChildFragmentAdapter
import com.lowe.wanandroid.ui.message.MessageTabBean
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore
import dagger.hilt.android.AndroidEntryPoint

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

    private val messagesAdapter = MultiTypeAdapter()
    private val messageTabBean by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(MessageChildFragmentAdapter.KEY_MESSAGE_CHILD_TAB_PARCELABLE)
            ?: MessageTabBean(MessageChildFragmentAdapter.MESSAGE_TAB_NEW)
    }

    override val viewModel: MessageTabChildViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
        loadMessages()
    }

    private fun initView() {
        messagesAdapter.register(MessageTabChildItemBinder(this::onMsgItemClick))
        viewBinding.apply {
            with(messageList) {
                adapter = messagesAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                loadMore(loadFinish = { viewModel.isLoading.not() }) {
                    loadMessages()
                }
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            messageListLiveData.observe(viewLifecycleOwner) {
                dispatchToAdapter(it, messagesAdapter)
            }
        }
    }

    private fun loadMessages() {
        if (messageTabBean.title == MessageChildFragmentAdapter.MESSAGE_TAB_NEW) {
            viewModel.getUnreadMsg()
        } else {
            viewModel.getReadiedMsg()
        }
    }

    private fun dispatchToAdapter(
        result: Pair<List<Any>, DiffUtil.DiffResult>,
        adapter: MultiTypeAdapter
    ) {
        adapter.items = result.first
        result.second.dispatchUpdatesTo(adapter)
    }

    private fun onMsgItemClick(position: Int, msgBean: MsgBean) {
        WebActivity.loadUrl(requireContext(), msgBean.fullLink)
    }
}