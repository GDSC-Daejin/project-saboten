package backend.common;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import backend.model.user.VoteSelectEntity;
import lombok.Getter;

@Getter
public class EntityFactory {

    public static UserEntity basicUserEntity(){
        return UserEntity.builder()
                .nickname("일반 사용자")
                .build();
    }

    public static UserEntity authorUserEntity(){
        return UserEntity.builder()
                .nickname("작성자")
                .build();
    }


    public static PostEntity basicPostEntity(){
        return PostEntity.builder()
                .postLikeCount(0)
                .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
                .user(authorUserEntity())
                .build();
    }

    public static CategoryEntity basicCategoryEntity(){
        return CategoryEntity.builder()
                .categoryName("음악")
                .iconUrl("https://www.google.com")
                .build();
    }

    public static CommentEntity basicCommentEntity(){
        return CommentEntity.builder()
                .commentText("민트초코가 짱이라 치약에 민트맛을 넣은거에요! 매일 맛봐도 안질리니까")
                .commentLikeCount(100L)
                .build();
    }

    public static CategoryInPostEntity basicCategoryInPostEntity(){
        return CategoryInPostEntity.builder()
                .post(basicPostEntity())
                .category(basicCategoryEntity())
                .build();
    }

    public static PostLikeEntity basicPostLikeEntity() {
        return PostLikeEntity.builder()
                .post(basicPostEntity())
                .user(basicUserEntity())
                .build();
    }

    public static VoteSelectEntity basicVoteSelectEntity() {
        return VoteSelectEntity.builder()
                .post(basicPostEntity())
                .user(basicUserEntity())
                .voteResult(1)
                .build();
    }

    public static RefreshTokenEntity basicRefreshTokenEntity() {
        return RefreshTokenEntity.builder()
                .refreshToken("JWTJWTJWTJWT")
                .user(basicUserEntity())
                .build();
    }

}

//    // given
//    private UserEntity author = UserEntity.builder()
//            .nickname("작성자")
//            .build();
//    private PostEntity post = PostEntity.builder()
//            .postLikeCount(0)
//            .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
//            .userId(author)
//            .build();



//    // given
//    private UserEntity author = UserEntity.builder()
//            .nickname("작성자")
//            .build();
//    private PostEntity post = PostEntity.builder()
//            .postLikeCount(0)
//            .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
//            .userId(author)
//            .build();
//    private UserEntity user = UserEntity.builder()
//            .nickname("일반 사용자")
//            .build();
