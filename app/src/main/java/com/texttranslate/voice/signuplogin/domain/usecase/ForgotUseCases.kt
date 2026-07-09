package com.texttranslate.voice.signuplogin.domain.usecase

data class ForgotUseCases(
    val resetPasswordUseCase: ResetPasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase
)
