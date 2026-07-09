package com.texttranslate.voice.signuplogin.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.texttranslate.voice.HomeActivity
import com.texttranslate.voice.signuplogin.navigation.RootNavGraph
import com.texttranslate.voice.signuplogin.ui.theme.FirebaseAuthenticationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // ✅ 1. Install Splash Screen API (MUST be called before super.onCreate)
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)

        // ✅ 2. Keep the splash screen visible for a short time
        // This ensures the logo is seen even if the app loads instantly.
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        lifecycleScope.launch {
            // Check login status
            val currentUser = FirebaseAuth.getInstance().currentUser
            
            // Give the user 2 seconds to see your logo
            delay(2000)
            keepSplashScreen = false

            if (currentUser != null) {
                // User is already logged in -> Home
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            } else {
                // User is not logged in -> Show Compose Login Screen
                setContent {
                    FirebaseAuthenticationTheme {
                        RootNavGraph(navController = rememberNavController(), context = this@MainActivity)
                    }
                }
            }
        }
    }
}
