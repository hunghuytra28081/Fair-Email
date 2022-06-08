package com.photo.fairemail.binding

import android.os.Build
import android.text.Html

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["bind:setTextHtml"])
fun TextView.setTextHTML(html: String?) {
    text = when {
        html == null -> {
            "Don't have Summary"
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        }
        else -> {
            Html.fromHtml(html)
        }
    }
}
