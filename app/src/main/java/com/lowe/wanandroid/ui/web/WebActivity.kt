package com.lowe.wanandroid.ui.web

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityWebLayoutBinding
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.intentTo

class WebActivity :
    BaseActivity<WebViewModel, ActivityWebLayoutBinding>(R.layout.activity_web_layout) {

    companion object {

        fun loadUrl(context: Context, url: String) {
            context.startActivity(intentTo(Activities.Web(bundle = bundleOf(Activities.Web.KEY_WEB_VIEW_URL to url))))
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
        intent.getStringExtra(Activities.Web.KEY_WEB_VIEW_URL) ?: ""
    }

    override val viewModel: WebViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        agentWeb.urlLoader.loadUrl(url)
    }
}