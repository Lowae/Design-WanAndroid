package com.lowe.wanandroid.ui.home.child.square

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildSquareBinding
import com.lowe.wanandroid.ui.BaseFragment

class SquareFragment :
    BaseFragment<SquareViewModel, FragmentHomeChildSquareBinding>(R.layout.fragment_home_child_square) {

    companion object {
        fun newInstance() = SquareFragment()
    }

    override fun createViewModel() = SquareViewModel()

    override fun init(savedInstanceState: Bundle?) {
    }
}