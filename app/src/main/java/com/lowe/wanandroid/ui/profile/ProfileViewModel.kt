package com.lowe.wanandroid.ui.profile

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) :
    BaseViewModel() {

    val profileLiveData = MutableLiveData<UserBaseInfo>()

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