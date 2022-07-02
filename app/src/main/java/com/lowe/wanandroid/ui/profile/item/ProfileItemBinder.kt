package com.lowe.wanandroid.ui.profile.item

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemProfileOptionsLayoutBinding
import com.lowe.wanandroid.ui.ViewBindingHolder
import com.lowe.wanandroid.ui.profile.ProfileItemBean
import com.lowe.wanandroid.utils.getPrimaryColor

class ProfileItemBinder(private val onClick: (Int, ProfileItemBean) -> Unit) :
    ItemViewBinder<ProfileItemBean, ViewBindingHolder<ItemProfileOptionsLayoutBinding>>() {

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
        holder.binding.apply {
            this.item = item
            executePendingBindings()
            profileItemIcon.setImageDrawable(
                AppCompatResources.getDrawable(root.context, item.iconRes)?.apply {
                    colorFilter = PorterDuffColorFilter(
                        root.context.getPrimaryColor(),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            )
            root.setOnClickListener { onClick(holder.bindingAdapterPosition, item) }
        }
    }
}