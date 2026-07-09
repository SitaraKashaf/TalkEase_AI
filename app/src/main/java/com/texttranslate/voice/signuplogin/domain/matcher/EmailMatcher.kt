package com.texttranslate.voice.signuplogin.domain.matcher

interface EmailMatcher {
    fun isValid(email: String): Boolean
}