package com.lowe.wanandroid.ui.group

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.group.repository.GroupRepository
import com.lowe.wanandroid.ui.launch

class GroupViewModel : BaseViewModel() {

    val authorsNameLiveData = MutableLiveData<List<Classify>>()
    val parentRefreshLiveData = MutableLiveData<Int>()
    val scrollToTopLiveData = MutableLiveData<Int>()

    override fun start() {
        fetchAuthorsName()
    }

    private fun fetchAuthorsName() {
        launch({
            authorsNameLiveData.value =
                GroupRepository.getAuthorTitleList().success()?.data ?: emptyList()
        })
    }

}