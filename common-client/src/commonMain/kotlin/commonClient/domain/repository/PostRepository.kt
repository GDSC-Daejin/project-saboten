package commonClient.domain.repository

import common.model.reseponse.PagingResponse
import common.model.reseponse.paging.NewPagingResponse
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post

interface PostRepository {

    suspend fun postsById(postId: Long): Post

    suspend fun getPagedPosts(
        categoryId: Long?,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ): NewPagingResponse<Post>

    suspend fun getRecentPosts(): NewPagingResponse<Post>
    suspend fun getSelectedPosts(): NewPagingResponse<Post>

    suspend fun getPagedHotPost(
        categoryId: Long?,
        duration: Duration?,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ): NewPagingResponse<Post>

    suspend fun getPagedSearchPost(searchText: String, offset: Int?, pageNumber: Int?, pageSize: Int?): NewPagingResponse<Post>
}