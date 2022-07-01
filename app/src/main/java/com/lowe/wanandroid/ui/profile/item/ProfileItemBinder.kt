package com.lowe.wanandroid.ui.profile.item

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.binder.ItemViewDataBindingBinder
import com.lowe.wanandroid.base.binder.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemProfileOptionsLayoutBinding
import com.lowe.wanandroid.ui.profile.ProfileItemBean
import com.lowe.wanandroid.utils.getPrimaryColor

class ProfileItemBinder(private val onClick: (Int, ProfileItemBean) -> Unit) :
    ItemViewDataBindingBinder<ProfileItemBean, ViewBindingHolder<ItemProfileOptionsLayoutBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemProfileOptionsLayoutBinding> = ViewBindingHolder(
        DataBindingUtil.inflate(
            inflater,
            R.layout.item_profile_options_layout,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewBindingHolder<ItemProfileOptionsLayoutBinding>,
        item: ProfileItemBean
    ) {
        super.onBindViewHolder(holder, item)
        holder.binding.apply {
            this.item = item
            profileItemIcon.setImageDrawable(
                AppCompatResources.getDrawable(root.context, item.iconRes)?.apply {
                    colorFilter = PorterDuffColorFilter(
                        root.context.getPrimaryColor(),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            )
            executePendingBindings()
        }
    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)
        onClick(position, adapterItems[position] as ProfileItemBean)
    }
}