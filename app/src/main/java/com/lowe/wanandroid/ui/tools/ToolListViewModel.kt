package com.lowe.wanandroid.ui.tools

import androidx.lifecycle.liveData
import com.lowe.common.base.BaseViewModel
import com.lowe.common.base.http.adapter.getOrElse
import com.lowe.common.services.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToolListViewModel @Inject constructor(private val profileService: ProfileService) :
    BaseViewModel() {

    val toolsLiveData = liveData {
        emit(
            profileService.getToolList().getOrElse { emptyList() }
        )
    }

}