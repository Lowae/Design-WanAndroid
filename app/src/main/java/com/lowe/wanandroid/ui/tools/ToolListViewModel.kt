package com.lowe.wanandroid.ui.tools

import androidx.lifecycle.liveData
import com.lowe.wanandroid.base.http.adapter.getOrElse
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.ui.BaseViewModel
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