package com.texttranslate.voice.signuplogin.domain.usecase

import com.texttranslate.voice.signuplogin.domain.repository.AuthRepository

class ResetPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.resetPassword(email = email)
    }
}