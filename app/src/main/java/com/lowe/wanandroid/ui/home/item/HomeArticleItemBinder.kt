package com.lowe.wanandroid.ui.home.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.drakeet.multitype.ItemViewBinder
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.ViewBindingHolder
import com.lowe.wanandroid.databinding.ItemHomeArticleLayoutBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.web.WebActivity

class HomeArticleItemBinder : ItemViewBinder<Article, ViewBindingHolder<ItemHomeArticleLayoutBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBindingHolder<ItemHomeArticleLayoutBinding> {
        return ViewBindingHolder(DataBindingUtil.inflate(inflater, R.layout.item_home_article_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewBindingHolder<ItemHomeArticleLayoutBinding>, item: Article) {
        holder.binding.apply {
            article = item
            executePendingBindings()
            clItem.setOnClickListener {
                holder.itemView.context.startActivity(Intent(holder.itemView.context, WebActivity::class.java))
                Toast.makeText(root.context, "item click ${holder.bindingAdapterPosition}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}