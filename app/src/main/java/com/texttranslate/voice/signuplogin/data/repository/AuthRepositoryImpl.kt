package com.texttranslate.voice.signuplogin.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.texttranslate.voice.signuplogin.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): AuthRepository {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // EMAIL & PASSWORD
    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(authResult.user)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(authResult.user)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // SIGN OUT
    override fun signOut() {
        firebaseAuth.signOut()
    }
}