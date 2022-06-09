package com.photo.fairemail.extension

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.SystemClock
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.photo.fairemail.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_account.view.*

fun Dialog.showCustomDialog(layoutId: Int, isCancelable: Boolean) {
    this.apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutId)

        val window = window ?: return

        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val attributes = window.attributes
        attributes.gravity = Gravity.CENTER
        window.attributes = attributes
        setCancelable(isCancelable)
        show()
    }
}

fun View.viewGone() {
    this.visibility = View.GONE
}

fun View.viewVisible() {
    this.visibility = View.VISIBLE
}

fun View.viewInVisible() {
    this.visibility = View.INVISIBLE
}

fun Activity.transparentStatusBar() {
    if (Build.VERSION.SDK_INT > 19 && Build.VERSION.SDK_INT < 21) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    }
    if (Build.VERSION.SDK_INT >= 19) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }
}

private fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val win = window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun View.setOnSingerClick(debounceTime: Long = 500, action: () -> Unit) {

    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun customTextViewAgree(activity: Activity, view: TextView, viewMoveTop: View, viewMoveTop2: View) {
    val spanTxt = SpannableStringBuilder(
        "By continuing you agree to the Spark ")
    spanTxt.append("Terms of service")
    spanTxt.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            activity.hideKeyboard()
            viewMoveTop.animate().translationY(0F).duration = 1000
        }
    }, spanTxt.length - "Term of services".length, spanTxt.length, 0)
    spanTxt.append(" and")
    spanTxt.append(" Privacy Policy")
    spanTxt.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            activity.hideKeyboard()
            viewMoveTop2.animate().translationY(0F).duration = 1000
        }
    }, spanTxt.length - " Privacy Policy".length, spanTxt.length, 0)
    view.movementMethod = LinkMovementMethod.getInstance()
    view.setText(spanTxt, TextView.BufferType.SPANNABLE)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun WebView.loadWebView(url: String){
    this.apply {
        settings.javaScriptEnabled = true
        loadUrl(url)
        webViewClient = WebViewClient()
    }
}
