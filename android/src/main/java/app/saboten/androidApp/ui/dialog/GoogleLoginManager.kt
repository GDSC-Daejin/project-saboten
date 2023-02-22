package app.saboten.androidApp.ui.dialog

import android.app.Activity
import android.content.Context
import android.content.Intent
import app.saboten.androidApp.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlin.coroutines.suspendCoroutine

class GoogleLoginManager() {

    lateinit var oneTapClient: SignInClient

    suspend fun signInIntent(activity: Activity)  = suspendCoroutine { continuation ->

        oneTapClient = Identity.getSignInClient(activity)
        val signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(activity.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnFailureListener { continuation.resumeWith(Result.failure(it)) }
            .addOnSuccessListener {
                continuation.resumeWith(Result.success(it.pendingIntent))
            }
    }

}