package commonClient.data.repository

import common.model.Post
import commonClient.data.LoadState
import commonClient.domain.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class PostRepositoryImp : PostRepository {
    // TODO 진짜 코드로 교체해야함
    override fun getPosts() = flow {
        emit(LoadState.loading())
        delay(1000)
        emit(LoadState.success(listOf<Post>()))
    }
}