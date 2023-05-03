package commonClient.domain.repository

import common.model.request.post.VoteSelectRequest
import common.model.reseponse.paging.PagingResponse
import common.model.request.post.create.PostCreateRequest
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post

interface PostRepository {

    suspend fun votePost(postId: Long, voteSelectRequest: VoteSelectRequest) : Post

    suspend fun scrapPost(postId: Long) : Post

    suspend fun likePost(postId: Long) : Post

    suspend fun postsById(postId: Long): Post

    suspend fun getPosts(categoryId: Long?, pagingRequest: PagingRequest) : PagingResponse<Post>

    suspend fun getRecentPosts(pagingRequest: PagingRequest): PagingResponse<Post>

    suspend fun getHotPosts(categoryId: Long?, duration: Duration?, pagingRequest: PagingRequest): PagingResponse<Post>

    suspend fun getMyPosts(pagingRequest: PagingRequest): PagingResponse<Post>

    suspend fun getMyScrappedPosts(pagingRequest: PagingRequest): PagingResponse<Post>

    suspend fun getMyVotedPosts(pagingRequest: PagingRequest): PagingResponse<Post>

    suspend fun getSearchPosts(searchText: String, pagingRequest: PagingRequest): PagingResponse<Post>

    suspend fun getSearchedPostCount(searchText: String): Long

    suspend fun createPost(postCreateRequest: PostCreateRequest): Post

    suspend fun deletePost(postId: Long): String

}