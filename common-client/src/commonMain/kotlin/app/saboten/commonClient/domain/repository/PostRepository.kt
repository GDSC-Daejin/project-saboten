package app.saboten.commonClient.domain.repository

import app.saboten.common.entities.Post
import app.saboten.commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<LoadState<List<Post>>>

}