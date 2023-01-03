package commonClient.domain.usecase.post

import common.model.reseponse.PagingResponse
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.post.Vote
import commonClient.domain.entity.post.VoteColors
import commonClient.domain.entity.user.User
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPagedPostByCategoryIdUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(
        categoryId: Long,
        nextKey: Long?
    )/*    = postRepository.getPagedPost(categoryId, nextKey)*/ = PagingResponse(
        data = (0..9).map {
            Post(
                it.toLong(),
                "탕수육 먹을때 찍먹 vs 부먹",
                User(0, "Harry", "https://picsum.photos/200/200"),
                listOf(
                    Vote(0, "찍먹", 10, VoteColors.BLUE),
                    Vote(1, "부먹", 1, VoteColors.RED)
                ),
                listOf(
                    Category(0, "호불호", ""),
                    Category(0, "먹을거", "")
                ),
                0,
                null,
                null,
                "",
                null
            )
        },
        nextKey = 10,
        count = 20
    )
}
