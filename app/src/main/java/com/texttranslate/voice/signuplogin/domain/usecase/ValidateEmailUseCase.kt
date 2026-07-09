package com.texttranslate.voice.signuplogin.domain.usecase

import com.texttranslate.voice.signuplogin.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}