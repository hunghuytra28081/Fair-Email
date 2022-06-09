package com.photo.fairemail.ui.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.photo.fairemail.R
import com.photo.fairemail.extension.transparentStatusBar
import kotlinx.android.synthetic.main.activity_email_provider.*

class EmailProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_provider)

        initView()
        initHandles()
    }

    private fun initView() {
        transparentStatusBar()
    }

    private fun initHandles() {
        img_close_email_provider.setOnClickListener {
            finish()
        }
    }
}
