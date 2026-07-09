package com.texttranslate.voice.signuplogin.ui.screen.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.texttranslate.voice.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLogin = {
                    // Navigate to HomeActivity after successful login
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                },
                onForgot = { /* TODO: Navigate to Forgot Screen */ },
                onSignUp = { /* TODO: Navigate to Signup Screen */ }
            )
        }
    }
}