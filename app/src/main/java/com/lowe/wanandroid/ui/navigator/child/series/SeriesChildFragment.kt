package com.lowe.wanandroid.ui.navigator.child.series

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNavigatorChildSeriesBinding
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean

class SeriesChildFragment :
    BaseFragment<SeriesChildViewModel, FragmentNavigatorChildSeriesBinding>(R.layout.fragment_navigator_child_series) {

    companion object {
        fun newInstance(navigatorTabBean: NavigatorTabBean): SeriesChildFragment = with(
            SeriesChildFragment()
        ) {
            arguments?.apply {
                putParcelable(
                    NavigatorFragment.KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE,
                    navigatorTabBean
                )
            }
            this
        }
    }

    override fun createViewModel() = SeriesChildViewModel()

    override fun init(savedInstanceState: Bundle?) {

    }
}