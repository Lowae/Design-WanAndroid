package com.lowe.wanandroid.ui.setting

import androidx.lifecycle.ViewModel
import com.lowe.common.account.IAccountViewModelDelegate
import com.lowe.common.di.ApplicationScope
import com.lowe.common.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val accountViewModelDelegate: IAccountViewModelDelegate,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), IAccountViewModelDelegate by accountViewModelDelegate {

    fun userLogout() {
        applicationScope.launch(ioDispatcher) {
            logout()
        }
    }

}