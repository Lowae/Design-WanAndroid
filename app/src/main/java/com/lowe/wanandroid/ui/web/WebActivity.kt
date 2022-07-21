package com.lowe.wanandroid.ui.web

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.compat.IntentCompat
import com.lowe.wanandroid.databinding.ActivityWebLayoutBinding
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.getPrimaryColor
import com.lowe.wanandroid.utils.intentTo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebActivity : BaseActivity<WebViewModel, ActivityWebLayoutBinding>() {

    companion object {

        fun loadUrl(context: Context, data: Activities.Web.WebIntent) {
            context.startActivity(intentTo(Activities.Web(bundle = bundleOf(Activities.Web.KEY_WEB_VIEW_Intent_bundle to data))))
        }

        fun loadUrl(context: Context, url: String) {
            context.startActivity(
                intentTo(
                    Activities.Web(
                        bundle = bundleOf(
                            Activities.Web.KEY_WEB_VIEW_Intent_bundle to Activities.Web.WebIntent(
                                url
                            )
                        )
                    )
                )
            )
        }

    }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val agentWeb by lazy {
        AgentWeb.with(this)
            .setAgentWebParent(
                viewDataBinding.webContainer as ViewGroup,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator(getPrimaryColor())
            .setWebViewClient(object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    currentUrl = url.orEmpty()
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (!request?.url.toString().startsWith("http")) {
                        return true
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            })
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    viewDataBinding.title.text = title
                }
            })
            .createAgentWeb()
            .ready()
            .get()
    }

    private val intentData by lazy {
        IntentCompat.getParcelableExtra(intent, Activities.Web.KEY_WEB_VIEW_Intent_bundle)
            ?: Activities.Web.WebIntent("")
    }

    private var currentUrl = ""

    override val viewDataBinding: ActivityWebLayoutBinding by ActivityDataBindingDelegate(R.layout.activity_web_layout)

    override val viewModel: WebViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        agentWeb.urlLoader.loadUrl(intentData.url)
        initView()
        viewModel.webDataObservable.set(intentData)
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (agentWeb.handleKeyEvent(keyCode, event)) return true
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (!agentWeb.back()) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.web_action_refresh -> {
                agentWeb.urlLoader.reload()
                true
            }
            R.id.web_action_share -> {
                startActivity(
                    Intent.createChooser(
                        Intent()
                            .setAction(Intent.ACTION_SEND)
                            .putExtra(
                                Intent.EXTRA_TEXT,
                                "${viewDataBinding.title.text}:${currentUrl}"
                            )
                            .setType("text/plain"), "分享至"
                    )
                )
                true
            }
            R.id.web_action_open_outside -> {
                startActivity(
                    Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse(currentUrl))
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        viewDataBinding.apply {
            intentObservable = viewModel.webDataObservable
            notifyPropertyChanged(BR.intentObservable)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                if (!agentWeb.back()) {
                    finish()
                }
            }
            collect.setOnClickListener { changeCollectStatus() }
        }
    }

    private fun changeCollectStatus() {
        intentData.isCollected = intentData.isCollected.not()
        appViewModel.articleCollectAction(
            CollectEvent(
                intentData.id,
                intentData.url,
                intentData.isCollected
            )
        )
        viewModel.webDataObservable.notifyChange()
    }
}