package commonClient.domain.repository

import common.model.reseponse.PagingResponse
import common.model.reseponse.paging.NewPagingResponse
import commonClient.domain.entity.post.Post

interface PostRepository {

    suspend fun postsById(postId: Long): Post

    suspend fun getPagedPost(categoryId : Long?, nextKey : Long?) : PagingResponse<Post>

    suspend fun getPagedHotPost(offset: Int?, pageNumber: Int?, pageSize: Int?): NewPagingResponse<Post>

    suspend fun getPagedSearchPost(searchText: String, offset: Int?, pageNumber: Int?, pageSize: Int?): NewPagingResponse<Post>
}