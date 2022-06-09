package com.photo.fairemail.ui.account

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.photo.fairemail.R
import com.photo.fairemail.extension.hideKeyboard
import com.photo.fairemail.extension.loadWebView
import com.photo.fairemail.extension.transparentStatusBar
import com.photo.fairemail.extension.viewGone
import com.photo.fairemail.ui.login.LoginActivity
import com.photo.fairemail.ui.provider.EmailProviderActivity
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        initView()
        initHandle()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        transparentStatusBar()
        val intentUrl = intent.getStringExtra(LoginActivity.INTENT_ACCOUNT_URL) ?: ""
        wv_account.apply {
            loadWebView(intentUrl)
            tv_url_web.text = url
            webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    layout_progress.viewGone()
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)

                }
            }
        }
    }

    private fun initHandle() {
        img_close_web.setOnClickListener {
            val intent = Intent(this,EmailProviderActivity::class.java)
            startActivity(intent)
            finish()
            hideKeyboard()
        }
    }

    override fun onBackPressed() {
        if (wv_account.canGoBack()) {
            wv_account.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
