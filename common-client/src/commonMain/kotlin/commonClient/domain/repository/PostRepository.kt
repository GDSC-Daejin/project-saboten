package commonClient.domain.repository

import common.model.request.post.VoteSelectRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.paging.NewPagingResponse
import common.model.reseponse.post.PostResponse
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post

interface PostRepository {

    suspend fun votePost(postId: Long, voteSelectRequest: VoteSelectRequest) : Post

    suspend fun scrapPost(postId: Long) : Post

    suspend fun likePost(postId: Long) : Post

    suspend fun postsById(postId: Long): Post

    suspend fun getPosts(categoryId: Long?, pagingRequest: PagingRequest) : NewPagingResponse<Post>

    suspend fun getRecentPosts(pagingRequest: PagingRequest): NewPagingResponse<Post>

    suspend fun getHotPosts(categoryId: Long?, duration: Duration?, pagingRequest: PagingRequest): NewPagingResponse<Post>

    suspend fun getMyPosts(pagingRequest: PagingRequest): NewPagingResponse<Post>

    suspend fun getMyScrappedPosts(pagingRequest: PagingRequest): NewPagingResponse<Post>

    suspend fun getMyVotedPosts(pagingRequest: PagingRequest): NewPagingResponse<Post>

    suspend fun getSearchPosts(searchText: String, pagingRequest: PagingRequest): NewPagingResponse<Post>

}