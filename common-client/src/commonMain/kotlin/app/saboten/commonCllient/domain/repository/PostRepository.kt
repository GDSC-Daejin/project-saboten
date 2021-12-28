package app.saboten.commonCllient.domain.repository

import app.saboten.common.entities.Post
import app.saboten.commonCllient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<LoadState<List<Post>>>

}