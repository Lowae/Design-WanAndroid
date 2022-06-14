package com.lowe.wanandroid.ui.profile

import androidx.annotation.DrawableRes
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) :
    BaseViewModel() {

    override fun start() {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        launch({
            repository.getServerUserInfo()
        })
    }

    fun userBaseInfoStateFlow() = repository.getUserBaseInfoStateFlow()

}

class ProfileItemBean(

    @DrawableRes
    val iconRes: Int,
    val title: String,
    val type: Type = Type.Action
) {
    sealed interface Type {

        object Action : Type

    }

}