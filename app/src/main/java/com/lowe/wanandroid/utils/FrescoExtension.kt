package com.lowe.wanandroid.utils

import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("imageUrl")
fun SimpleDraweeView.loadImage(url: String) {
    setImageURI(url)
}