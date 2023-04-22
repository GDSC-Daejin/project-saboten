package app.saboten.androidApp.ui.dialog

import android.app.Activity
import android.content.IntentSender
import app.saboten.androidApp.R
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.tasks.await

class GoogleLoginManager(private val activity: Activity) {

    suspend fun signInIntent() : IntentSender {
        val request = GetSignInIntentRequest.builder()
            .setServerClientId(activity.getString(R.string.default_web_client_id))
            .build()

        val intent = Identity.getSignInClient(activity).getSignInIntent(request).await()
        return intent.intentSender
    }

}