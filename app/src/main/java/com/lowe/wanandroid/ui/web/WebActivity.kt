package com.lowe.wanandroid.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityWebLayoutBinding
import com.lowe.wanandroid.ui.BaseActivity

class WebActivity :
    BaseActivity<WebViewModel, ActivityWebLayoutBinding>(R.layout.activity_web_layout) {

    companion object {
        const val KEY_WEB_VIEW_URL = "key_web_view_url"

        fun loadUrl(context: Context, url: String) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                putExtra(KEY_WEB_VIEW_URL, url)
            })
        }

    }

    private val agentWeb by lazy {
        AgentWeb.with(this)
            .setAgentWebParent(
                viewDataBinding.flWeb,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator()
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    viewDataBinding.toolbar.title = title
                }
            })
            .createAgentWeb()
            .ready()
            .get()
    }

    private val url by lazy {
        intent.getStringExtra(KEY_WEB_VIEW_URL) ?: ""
    }

    override fun createViewModel() = WebViewModel()

    override fun init(savedInstanceState: Bundle?) {
        agentWeb.urlLoader.loadUrl(url)
    }
}