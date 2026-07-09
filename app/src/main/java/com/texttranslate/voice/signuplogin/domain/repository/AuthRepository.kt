package com.texttranslate.voice.signuplogin.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    // User
    fun getCurrentUser(): FirebaseUser?

    // Email & password
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?>
    suspend fun resetPassword(email: String): Result<Unit>

    // Sign out
    fun signOut()
}