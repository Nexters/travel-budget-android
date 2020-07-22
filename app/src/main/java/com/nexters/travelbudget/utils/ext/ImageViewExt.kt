package com.nexters.travelbudget.utils.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * ImageView 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.18
 */

@BindingAdapter("imgRes")
fun ImageView.bindImageRes(resId: Int?) {
    if (resId != null) {
        Glide.with(this.context).load(resId).into(this)
    }
}

@BindingAdapter(value = ["imgUrl", "imgRadius"], requireAll = false)
fun ImageView.bindImageUrl(url: String?, radius: Float = 0F) {
    url?.let {
        if (radius == 0F) {
            Glide.with(this)
                .load(url)
                .into(this)
        } else {
            Glide.with(this)
                .load(url)
                .apply(RequestOptions().transform(RoundedCorners(radius.toInt())))
                .into(this)
        }
    }
}