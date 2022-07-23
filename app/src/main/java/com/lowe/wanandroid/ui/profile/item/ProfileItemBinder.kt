package com.lowe.wanandroid.ui.profile.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.color.MaterialColors
import com.lowe.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ItemProfileOptionsLayoutBinding
import com.lowe.resource.extension.getPrimaryColor
import com.lowe.wanandroid.ui.ViewBindingHolder
import com.lowe.wanandroid.ui.profile.Badge
import com.lowe.wanandroid.ui.profile.ProfileItemBean

class ProfileItemBinder(private val onClick: (Int, ProfileItemBean) -> Unit) :
    ItemViewBinder<ProfileItemBean, ProfileItemBinder.ProfileItemViewHolder>() {

    class ProfileItemViewHolder(override val binding: ItemProfileOptionsLayoutBinding) :
        ViewBindingHolder<ItemProfileOptionsLayoutBinding>(binding) {

        private var badgeDrawable: BadgeDrawable? = null

        fun updateBadge(badge: Badge) {
            if (badgeDrawable == null) {
                badgeDrawable = BadgeDrawable.create(binding.root.context).apply {
                    backgroundColor = MaterialColors.getColor(
                        binding.profileItemIcon,
                        com.google.android.material.R.attr.colorSecondary
                    )
                    badgeTextColor = MaterialColors.getColor(
                        binding.profileItemIcon,
                        com.google.android.material.R.attr.colorOnSecondary
                    )
                    number = 0
                    isVisible = false
                }
            }
            badgeDrawable?.also { drawable ->
                when (badge.type) {
                    Badge.BadgeType.NONE -> {
                        BadgeUtils.detachBadgeDrawable(drawable, binding.profileItemIcon)
                        badgeDrawable = null
                    }
                    Badge.BadgeType.DOT -> {
                        drawable.number = 0
                        drawable.isVisible = true
                        BadgeUtils.attachBadgeDrawable(drawable, binding.profileItemIcon)
                    }
                    Badge.BadgeType.NUMBER -> {
                        drawable.number = badge.number
                        drawable.isVisible = true
                        BadgeUtils.attachBadgeDrawable(drawable, binding.profileItemIcon)
                    }
                }
            }

        }


    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ProfileItemViewHolder = ProfileItemViewHolder(
        DataBindingUtil.inflate(
            inflater,
            R.layout.item_profile_options_layout,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ProfileItemViewHolder,
        item: ProfileItemBean
    ) {
        holder.binding.apply {
            this.item = item
            executePendingBindings()
            profileItemIcon.setImageDrawable(
                AppCompatResources.getDrawable(root.context, item.iconRes)?.apply {
                    setTint(root.context.getPrimaryColor())
                }
            )
            profileItemIcon.post {
                holder.updateBadge(item.badge)
            }
            root.setOnClickListener { onClick(holder.bindingAdapterPosition, item) }
        }
    }
}