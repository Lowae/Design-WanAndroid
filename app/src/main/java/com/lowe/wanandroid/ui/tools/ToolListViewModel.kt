package com.lowe.wanandroid.ui.tools

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lowe.wanandroid.services.ProfileService
import com.lowe.wanandroid.services.model.ToolBean
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToolListViewModel @Inject constructor(private val profileService: ProfileService) :
    BaseViewModel() {

    val toolsLiveData = MutableLiveData<List<ToolBean>>()

    override fun init() {
        super.init()
        viewModelScope.launch {
            toolsLiveData.value = getToolList().success()?.data ?: emptyList()
        }
    }

    private suspend fun getToolList() = profileService.getToolList()
}