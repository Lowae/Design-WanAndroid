package com.lowe.wanandroid.ui.web

import androidx.databinding.ObservableField
import com.lowe.common.base.BaseViewModel
import com.lowe.common.utils.Activities

class WebViewModel : BaseViewModel() {

    val webDataObservable = ObservableField<Activities.Web.WebIntent>()
}