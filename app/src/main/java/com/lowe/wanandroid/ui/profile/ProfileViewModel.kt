package com.lowe.wanandroid.ui.profile

import androidx.annotation.DrawableRes
import com.lowe.wanandroid.ui.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    override fun start() {

    }
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