package com.texttranslate.voice.signuplogin.data.repository

import android.util.Patterns
import com.texttranslate.voice.signuplogin.domain.matcher.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}