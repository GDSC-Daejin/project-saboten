package commonClient.domain.mapper

import common.model.reseponse.post.PostCountResponse
import commonClient.domain.entity.user.MyPageCount

fun PostCountResponse.toDomain() = MyPageCount(
    myPost = myPost,
    scrapedPost = scrapedPost,
    votedPost = votedPost,
)