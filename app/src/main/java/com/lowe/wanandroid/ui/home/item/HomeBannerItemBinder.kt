package com.lowe.wanandroid.ui.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.lowe.multitype.PagingItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemHomeBannerLayoutBinding
import com.lowe.wanandroid.services.model.Banner
import com.lowe.wanandroid.services.model.Banners
import com.youth.banner.indicator.CircleIndicator

class HomeBannerItemBinder(onClick: (Banner, Int) -> Unit) :
    PagingItemViewBinder<Banners, ViewBindingHolder<ItemHomeBannerLayoutBinding>>() {

    private val bannerAdapter = HomeBannerAdapter(emptyList(), onClick)

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemHomeBannerLayoutBinding> {
        return ViewBindingHolder(
            DataBindingUtil.inflate<ItemHomeBannerLayoutBinding?>(
                inflater,
                R.layout.item_home_banner_layout,
                parent,
                false
            ).apply {
                with(banner) {
                    setAdapter(bannerAdapter)
                    indicator = CircleIndicator(context)
                    addBannerLifecycleObserver(findViewTreeLifecycleOwner())
                }
            }
        )
    }

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemHomeBannerLayoutBinding>,
        item: Banners
    ) {
        bannerAdapter.setDatas(item.banners)
    }
}