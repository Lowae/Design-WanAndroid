package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityTutorialChapterListLayoutBinding
import com.lowe.common.services.model.Article
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.BaseActivity
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.common.utils.Activities
import com.lowe.common.utils.launchRepeatOnStarted
import com.lowe.common.utils.unsafeLazy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialChapterListActivity :
    BaseActivity<TutorialChapterListViewModel, ActivityTutorialChapterListLayoutBinding>() {

    companion object {
        const val KEY_INTENT_TUTORIAL_ID = "key_intent_tutorial_id"
        const val KEY_INTENT_TUTORIAL_TITLE = "key_intent_tutorial_title"
    }

    private val chapterAdapter = MultiTypeAdapter()
    private val tutorialId by unsafeLazy {
        intent.getIntExtra(KEY_INTENT_TUTORIAL_ID, -1)
    }
    private val tutorialTitle by unsafeLazy {
        intent.getStringExtra(KEY_INTENT_TUTORIAL_TITLE).orEmpty()
    }


    override val viewDataBinding: ActivityTutorialChapterListLayoutBinding by ActivityDataBindingDelegate(
        R.layout.activity_tutorial_chapter_list_layout
    )

    override val viewModel: TutorialChapterListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initEvents()
        if (savedInstanceState == null) {
            viewModel.fetchChapterList(tutorialId)
        }
    }

    private fun initView() {
        chapterAdapter.register(TutorialChapterItemBinder(this::onItemClick))
        viewDataBinding.apply {
            with(chapterList) {
                adapter = chapterAdapter
                layoutManager = LinearLayoutManager(context)
            }
            with(toolbar) {
                title = tutorialTitle
                setNavigationOnClickListener { this@TutorialChapterListActivity.finish() }
            }
        }
    }

    private fun initEvents() {
        launchRepeatOnStarted {
            viewModel.chaptersFlow.collect {
                chapterAdapter.items = it
                chapterAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onItemClick(position: Int, article: Article) {
        WebActivity.loadUrl(
            this, Activities.Web.WebIntent(article.link)
        )
    }
}