package com.lowe.wanandroid.ui.web

import androidx.databinding.ObservableField
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.utils.Activities

class WebViewModel : BaseViewModel() {

    val webDataObservable = ObservableField<Activities.Web.WebIntent>()

    override fun init() {
    }
}