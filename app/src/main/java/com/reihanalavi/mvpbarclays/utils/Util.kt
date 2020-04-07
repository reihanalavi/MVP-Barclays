package com.reihanalavi.mvpbarclays.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.reihanalavi.mvpbarclays.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(uri: String?) {
    Picasso.get()
        .load(uri)
        .fit()
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadSource(view: ImageView, uri: String?) {
    view.loadImage(uri)
}