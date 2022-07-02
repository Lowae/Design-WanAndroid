package com.lowe.wanandroid.ui.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import com.lowe.wanandroid.BuildConfig
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityAboutBinding
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.utils.fromHtml

/**
 * 关于页面
 */
class AboutActivity : BaseActivity<AboutViewModel, ActivityAboutBinding>() {

    override val viewDataBinding: ActivityAboutBinding by ActivityDataBindingDelegate(R.layout.activity_about)

    override val viewModel: AboutViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.toolbar.setNavigationOnClickListener { finish() }
        viewDataBinding.version.text = getString(
            R.string.app_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE.toString()
        )
        viewDataBinding.aboutText.apply {
            text = """
<!doctype html>
<html>
<head>
<meta charset='UTF-8'><meta name='viewport' content='width=device-width initial-scale=1'>
<title></title>
</head>
<body><h2 id='应用介绍'>应用介绍</h2>
<p>🦄<a href='https://github.com/Lowae/WanAndroid'>Design WanAndroid</a>，本应用是<a href='https://www.wanandroid.com/'>WanAndroid</a>网站的Android客户端。是<strong>Material Design</strong> + <strong>Jetpack</strong>最佳实践，严格遵循<strong>Material3</strong>设计，且完美支持其Dynamic Colors等新特性，贯彻<strong>MVVM</strong>架构，保证UI风格、逻辑设计的一致性。</p>
<p>&nbsp;</p>
<h2 id='网站内容'>网站内容</h2>
<p>本网站每天新增20~30篇优质文章，并加入到现有分类中，力求整理出一份优质而又详尽的知识体系，闲暇时间不妨上来学习下知识； 除此以外，并为大家提供平时开发过程中常用的工具以及常用的网址导航。</p>
<p>当然这只是我们目前的功能，未来我们将提供更多更加便捷的功能...</p>
<p>如果您有任何好的建议:</p>
<ul>
<li>关于网站排版</li>
<li>关于新增常用网址以及工具</li>
<li>未来你希望增加的功能等</li>

</ul>
<p>可以在 <a href='https://github.com/hongyangAndroid/wanandroid'>https://github.com/hongyangAndroid/xueandroid</a> 项目中以issue的形式提出，我将及时跟进。</p>
</body>
</html>
        """.trimIndent().fromHtml()
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

}