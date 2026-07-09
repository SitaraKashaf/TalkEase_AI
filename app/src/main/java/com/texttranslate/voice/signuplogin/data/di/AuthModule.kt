package com.texttranslate.voice.signuplogin.data.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.texttranslate.voice.signuplogin.data.repository.AuthRepositoryImpl
import com.texttranslate.voice.signuplogin.data.repository.EmailMatcherImpl
import com.texttranslate.voice.signuplogin.domain.matcher.EmailMatcher
import com.texttranslate.voice.signuplogin.domain.repository.AuthRepository
import com.texttranslate.voice.signuplogin.domain.usecase.ForgotUseCases
import com.texttranslate.voice.signuplogin.domain.usecase.LoginUseCases
import com.texttranslate.voice.signuplogin.domain.usecase.LoginWithEmailUseCase
import com.texttranslate.voice.signuplogin.domain.usecase.ResetPasswordUseCase
import com.texttranslate.voice.signuplogin.domain.usecase.SignupUseCases
import com.texttranslate.voice.signuplogin.domain.usecase.SignupWithEmailUseCase
import com.texttranslate.voice.signuplogin.domain.usecase.ValidateEmailUseCase
import com.texttranslate.voice.signuplogin.domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun authRepositoryImpl(@ApplicationContext context: Context): AuthRepository {
        return AuthRepositoryImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideLoginUseCases(
        repository: AuthRepository,
        emailMatcher: EmailMatcher
    ): LoginUseCases {
        return LoginUseCases(
            loginWithEmailUseCase = LoginWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideSignupUseCases(
        repository: AuthRepository,
        emailMatcher: EmailMatcher
    ): SignupUseCases {
        return SignupUseCases(
            signupWithEmailUseCase = SignupWithEmailUseCase(
                repository
            ),
            validateEmailUseCase = ValidateEmailUseCase(
                emailMatcher
            ),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideForgotUseCases(
        repository: AuthRepository,
        emailMatcher: EmailMatcher
    ): ForgotUseCases {
        return ForgotUseCases(
            resetPasswordUseCase = ResetPasswordUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher)
        )
    }

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }
}