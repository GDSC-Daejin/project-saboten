package commonClient.domain.repository

import common.entities.Post
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<LoadState<List<Post>>>

}