package com.lowe.wanandroid.utils

import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.lowe.wanandroid.R

@BindingAdapter("imageUrl")
fun SimpleDraweeView.loadImage(url: String) {
    hierarchy.apply {
        setPlaceholderImage(context.getColor(R.color.placeholer_gray_300).toDrawable())
    }
    controller = Fresco.newDraweeControllerBuilder()
        .setImageRequest(
            ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setImageDecodeOptions(
                    ImageDecodeOptions.newBuilder().setBitmapConfig(Bitmap.Config.RGB_565).build()
                )
                .build()
        )
        .build()
}