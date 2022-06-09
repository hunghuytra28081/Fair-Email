package com.photo.fairemail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.photo.fairemail.extension.transparentStatusBar
import com.photo.fairemail.ui.main.MainActivity
import com.photo.fairemail.ui.slide.SlideIntroActivity

class LaunchApp : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transparentStatusBar()
        preferences = getSharedPreferences(SLIDE_INTRO, Context.MODE_PRIVATE)

        val isFirstRun: Boolean = preferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            val intent = Intent(this, SlideIntroActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object{
        const val SLIDE_INTRO = "slide_intro"
    }
}