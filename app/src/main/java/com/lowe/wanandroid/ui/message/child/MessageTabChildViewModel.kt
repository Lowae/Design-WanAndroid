package com.lowe.wanandroid.ui.message.child

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.model.MsgBean
import com.lowe.wanandroid.base.SimpleDiffCallback
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.message.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageTabChildViewModel @Inject constructor(private val repository: MessageRepository) :
    BaseViewModel() {

    val messageListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    var isLoading = false

    private var page = 1

    fun getReadiedMsg() {
        isLoading = true
        launch({
            val result = repository.getReadiedMsgList(page)
            val oldList = messageListLiveData.value?.first ?: emptyList()
            messageListLiveData.value =
                getDiffResultPair(oldList, oldList + (result.success()?.data?.datas ?: emptyList()))
            page++
            isLoading = false
        })
    }


    fun getUnreadMsg() {
        isLoading = true
        launch({
            val result = repository.getUnreadMsgList(page)
            val oldList = messageListLiveData.value?.first ?: emptyList()
            messageListLiveData.value =
                getDiffResultPair(oldList, oldList + (result.success()?.data?.datas ?: emptyList()))
            page++
            isLoading = false
        })
    }

    private fun getDiffResultPair(
        oldList: List<Any>,
        newList: List<Any>
    ) = newList to DiffUtil.calculateDiff(SimpleDiffCallback(oldList, newList,
        { oldItem, newItem ->
            when {
                oldItem is MsgBean && newItem is MsgBean -> oldItem.id == newItem.id
                else -> oldItem.javaClass == newItem.javaClass
            }
        }, { oldItem, newItem ->
            when {
                oldItem is MsgBean && newItem is MsgBean -> oldItem == newItem
                else -> oldItem.javaClass == newItem.javaClass && oldItem == newItem
            }

        })
    )

}