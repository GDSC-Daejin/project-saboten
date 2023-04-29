package commonClient.data.repository

import common.model.request.post.VoteSelectRequest
import common.model.request.post.create.PostCreateRequest
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

    override suspend fun getPosts(categoryId: Long?, pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getPosts(categoryId, pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getRecentPosts(pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getRecentPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getHotPosts(categoryId: Long?, duration: Duration?, pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getHotPosts(categoryId, duration, pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getMyPosts(pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getMyPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getMyScrappedPosts(pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getMyScrappedPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getMyVotedPosts(pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getMyVotedPosts(pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getSearchPosts(searchText: String, pagingRequest: PagingRequest): common.model.reseponse.paging.PagingResponse<Post> {
        return postApi.getSearchPosts(searchText, pagingRequest).data!!.map { it.toDomain() }
    }

    override suspend fun getSearchedPostCount(searchText: String): Long {
        return postApi.getSearchedPostCount(searchText).data!!
    }

    override suspend fun createPost(postCreateRequest: PostCreateRequest): Post {
        // TODO: 사용자 인증
        return postApi.createPost(postCreateRequest).data!!.toDomain()
    }

}