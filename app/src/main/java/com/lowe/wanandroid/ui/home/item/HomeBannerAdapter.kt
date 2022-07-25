package com.lowe.wanandroid.ui.home.item

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.lowe.common.services.model.Banner
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(items: List<Banner>, private val onClick: (Banner, Int) -> Unit) :
    BannerAdapter<Banner, HomeBannerAdapter.BannerViewHolder>(items) {

    class BannerViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(SimpleDraweeView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })
    }

    override fun onBindView(holder: BannerViewHolder, data: Banner, position: Int, size: Int) {
        (holder.view as? SimpleDraweeView)?.apply {
            setImageURI(data.imagePath)
            setOnClickListener { onClick(data, position) }
        }
    }
}