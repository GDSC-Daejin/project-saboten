package commonClient.domain.mapper

import common.model.VoteColorsResponse
import common.model.reseponse.category.CategoryResponse
import common.model.reseponse.comment.CommentResponse
import common.model.reseponse.post.PostResponse
import common.model.reseponse.post.VoteResponse
import commonClient.domain.entity.post.*

fun PostResponse.toDomain(): Post {
    return Post(
        id,
        text,
        author.toDomain(),
        voteResponses.map(VoteResponse::toDomain),
        categories.map(CategoryResponse::toDomain),
        selectedVote,
        isScraped,
        isLiked,
        createdAt,
        updatedAt
    )
}

fun CategoryResponse.toDomain(): Category {
    return Category(id, name, iconUrl, startColor, endColor)
}

fun VoteResponse.toDomain(): Vote {
    return Vote(
        id,
        topic,
        count,
        when (color) {
            VoteColorsResponse.WHITE -> VoteColors.WHITE
            VoteColorsResponse.RED -> VoteColors.RED
            VoteColorsResponse.GREEN -> VoteColors.GREEN
            VoteColorsResponse.BLUE -> VoteColors.BLUE
            VoteColorsResponse.YELLOW -> VoteColors.YELLOW
            VoteColorsResponse.PURPLE -> VoteColors.PURPLE
            VoteColorsResponse.PINK -> VoteColors.PINK
            VoteColorsResponse.ORANGE -> VoteColors.ORANGE
            VoteColorsResponse.BROWN -> VoteColors.BROWN
            VoteColorsResponse.BLACK -> VoteColors.BLACK
        }
    )
}

fun CommentResponse.toDomain(): Comment {
    return Comment(
        id,
        text,
        author.toDomain(),
        selectedVote,
        createdAt
    )
}
