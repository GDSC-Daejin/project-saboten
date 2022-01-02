package commonClient.domain.repository

import common.model.Post
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<LoadState<List<Post>>>

}