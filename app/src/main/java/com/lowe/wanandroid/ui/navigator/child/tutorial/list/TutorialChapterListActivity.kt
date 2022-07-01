package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityTutorialChapterListLayoutBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialChapterListActivity :
    BaseActivity<TutorialChapterListViewModel, ActivityTutorialChapterListLayoutBinding>() {

    companion object {
        const val KEY_INTENT_TUTORIAL_ID = "key_intent_tutorial_id"
    }

    private val chapterAdapter = MultiTypeAdapter()
    private val tutorialId by lazy(LazyThreadSafetyMode.NONE) {
        intent.getIntExtra(KEY_INTENT_TUTORIAL_ID, -1)
    }

    override val viewDataBinding: ActivityTutorialChapterListLayoutBinding by ActivityDataBindingDelegate(
        R.layout.activity_tutorial_chapter_list_layout
    )

    override val viewModel: TutorialChapterListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserve()
        viewModel.fetchChapterList(tutorialId)
    }

    private fun initView() {
        chapterAdapter.register(TutorialChapterItemBinder(this::onItemClick))
        viewDataBinding.apply {
            with(chapterList) {
                adapter = chapterAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initObserve() {
        viewModel.chaptersLiveData.observe(this) {
            chapterAdapter.items = it
            chapterAdapter.notifyItemRangeInserted(0, chapterAdapter.itemCount)
        }
    }

    private fun onItemClick(position: Int, article: Article) {
        WebActivity.loadUrl(
            this, Activities.Web.WebIntent(article.link)
        )
    }
}