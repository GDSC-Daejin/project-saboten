package commonClient.data.repository

import common.model.request.post.VoteSelectRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.PagingResponse
import common.model.reseponse.paging.NewPagingResponse
import common.model.reseponse.post.PostResponse
import commonClient.data.remote.endpoints.PostApi
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.PostRepository
import commonClient.utils.map
import org.koin.core.annotation.Single

@Single(binds = [PostRepository::class])
class PostRepositoryImp(
    private val postApi: PostApi,
) : PostRepository {

    override suspend fun votePost(postId: Long, voteSelectRequest: VoteSelectRequest): Post {
        return postApi.votePost(postId, voteSelectRequest).data!!.toDomain()
    }

    override suspend fun scrapPost(postId: Long): Post {
        return postApi.scrapPost(postId).data!!.toDomain()
    }

    override suspend fun likePost(postId: Long): Post {
        return postApi.likePost(postId).data!!.toDomain()
    }

    override suspend fun postsById(postId: Long): Post {
        return postApi.getPost(postId).data!!.toDomain()
    }

    override suspend fun getRecentPosts(pagingRequest: PagingRequest): NewPagingResponse<Post> {
        return postApi.getRecentPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getHotPosts(categoryId: Long?, duration: Duration?, pagingRequest: PagingRequest): NewPagingResponse<Post> {
        return postApi.getHotPosts(categoryId, duration, pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getMyPosts(pagingRequest: PagingRequest): NewPagingResponse<Post> {
        return postApi.getMyPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getMyScrappedPosts(pagingRequest: PagingRequest): NewPagingResponse<Post> {
        return postApi.getMyScrappedPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getMyVotedPosts(pagingRequest: PagingRequest): NewPagingResponse<Post> {
        return postApi.getMyVotedPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getSearchPosts(searchText: String, pagingRequest: PagingRequest): NewPagingResponse<Post> {
        return postApi.getSearchPosts(searchText, pagingRequest).data!!.map { it.toDomain() }
    }

}