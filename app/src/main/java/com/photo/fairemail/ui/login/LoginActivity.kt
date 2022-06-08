package com.photo.fairemail.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.photo.fairemail.R
import com.photo.fairemail.databinding.ActivityLoginBinding
import com.photo.fairemail.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
        initHandles()
    }

    private fun initView() {
        edt_email.doOnTextChanged { text, start, before, count ->
            if(isValidEmail(text)){
                layout_email.endIconDrawable = resources.getDrawable(R.drawable.ic_check_email)
                btn_start.setBackgroundResource(R.drawable.bg_btn_email_correct)
            }
            else{
                layout_email.endIconDrawable = null
                btn_start.setBackgroundResource(R.drawable.bg_btn_email_wrong)
            }
        }
    }

    private fun initHandles() {
        google_sign_in.setOnClickListener {
            signIn()
        }
    }

    private fun initData() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        google_sign_in.setSize(SignInButton.SIZE_STANDARD)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            intentActivity()
//            try {
//                task.getResult(ApiException::class.java)
//            } catch (e: ApiException) {
//                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
//            }
        }
        else{
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()
        }
    }

    private fun intentActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val TAG = "Main123"
    }
}