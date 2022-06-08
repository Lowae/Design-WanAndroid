package com.lowe.wanandroid.ui.web

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityWebLayoutBinding
import com.lowe.wanandroid.ui.BaseActivity

class WebActivity : BaseActivity<WebViewModel, ActivityWebLayoutBinding>(R.layout.activity_web_layout) {
    override fun createViewModel() = WebViewModel()

    override fun init(savedInstanceState: Bundle?) {
        viewDataBinding.web.loadUrl("https://www.baidu.com")
    }
}