package commonClient.domain.repository

import commonClient.domain.entity.auth.JwtToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun refreshToken(forceRefresh : Boolean) : Flow<JwtToken?>

    suspend fun requestGoogleLogin(idToken : String) : JwtToken

}