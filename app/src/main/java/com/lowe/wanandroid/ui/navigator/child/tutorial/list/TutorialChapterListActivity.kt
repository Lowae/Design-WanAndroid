package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityTutorialChapterListLayoutBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.ToastEx.showShortToast

class TutorialChapterListActivity :
    BaseActivity<TutorialChapterListViewModel, ActivityTutorialChapterListLayoutBinding>(R.layout.activity_tutorial_chapter_list_layout) {

    companion object {
        const val KEY_INTENT_TUTORIAL_ID = "key_intent_tutorial_id"
    }

    private val chapterAdapter = MultiTypeAdapter()
    private val tutorialId by lazy(LazyThreadSafetyMode.NONE) {
        intent.getIntExtra(KEY_INTENT_TUTORIAL_ID, -1)
    }

    override fun createViewModel() = TutorialChapterListViewModel()

    override fun init(savedInstanceState: Bundle?) {
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

    private fun onItemClick(action: Pair<Int, Article>) {
        val (position, article) = action
        startActivity(
            Intent(
                this,
                WebActivity::class.java
            )
        )
        "pos: $position - name: ${article.title}".showShortToast()
    }
}