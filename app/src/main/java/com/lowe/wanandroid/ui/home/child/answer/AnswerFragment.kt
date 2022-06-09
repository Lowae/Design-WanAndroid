package com.lowe.wanandroid.ui.home.child.answer

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildAnswerBinding
import com.lowe.wanandroid.ui.BaseFragment

class AnswerFragment :
    BaseFragment<AnswerViewModel, FragmentHomeChildAnswerBinding>(R.layout.fragment_home_child_answer) {

    companion object {
        fun newInstance() = AnswerFragment()
    }

    override fun createViewModel() = AnswerViewModel()

    override fun init(savedInstanceState: Bundle?) {
    }
}