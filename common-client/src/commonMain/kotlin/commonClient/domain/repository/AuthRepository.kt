package commonClient.domain.repository

import common.model.reseponse.auth.JwtTokenResponse
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun refreshToken(forceRefresh : Boolean) : Flow<JwtTokenResponse?>

}