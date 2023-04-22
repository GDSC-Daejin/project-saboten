package backend.common;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.comment.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
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
                .age(20)
                .myPageIntroduction("안녕하세요. 다육이입니다.")
                .gender(1)
                .build();
    }

    public static UserEntity authorUserEntity(){
        return UserEntity.builder()
                .nickname("작성자")
                .age(20)
                .myPageIntroduction("안녕하세요. 다육이입니다.")
                .gender(1)
                .build();
    }

    public static PostEntity basicPostEntity(UserEntity user){
        return PostEntity.builder()
                .postLikeCount(0)
                .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
                .user(user)
                .postLikeCount(0)
                .postScrapCount(0)
                .view(0)
                .build();
    }

    public static CategoryEntity basicCategoryEntity(){
        return CategoryEntity.builder()
                .categoryName("애완동물")
                .categoryIconUrl("https://raw.githubusercontent.com/GDSC-Daejin/project-saboten-iconpack/master/ic_favorite.svg")
                .categoryStartColor("startColor")
                .categoryEndColor("endColor")
                .build();
    }

    public static CommentEntity basicCommentEntity(UserEntity user, PostEntity post){
        return CommentEntity.builder()
                .post(post)
                .user(user)
                .commentText("민트초코가 짱이라 치약에 민트맛을 넣은거에요! 매일 맛봐도 안질리니까")
                .commentLikeCount(100L)
                .build();
    }

    public static CommentEntity basicCommentEntity2(UserEntity user, PostEntity post){
        return CommentEntity.builder()
                .post(post)
                .user(user)
                .commentText("아 민트는 좀...")
                .commentLikeCount(50L)
                .build();
    }

    public static CommentEntity basicCommentEntity3(UserEntity user, PostEntity post){
        return CommentEntity.builder()
                .post(post)
                .user(user)
                .commentText("민트초코 맛을 모르다니,,,")
                .commentLikeCount(100L)
                .build();
    }

    public static VoteSelectEntity basicVoteSelectEntity(UserEntity user, PostEntity post){
        return VoteSelectEntity.builder()
                .user(user)
                .post(post)
                .voteResult(1L)  // 1번 Topic 2번 Topic 이라고 가정!
                .build();
    }

    public static VoteEntity basicVoteEntityTrue(PostEntity post){
        return VoteEntity.builder()
                .topic("민초파 찬성")
                .post(post)
                .count(20)
                .color("WHITE")
                .build();
    }
    public static VoteEntity basicVoteEntityFalse(PostEntity post){
        return VoteEntity.builder()
                .topic("민초파 반대")
                .post(post)
                .count(10)
                .color("WHITE")
                .build();
    }
    public static CategoryInPostEntity basicCategoryInPostEntity(PostEntity post, CategoryEntity category){
        return CategoryInPostEntity.builder()
                .post(post)
                .category(category)
                .build();
    }

    public static PostLikeEntity basicPostLikeEntity(UserEntity user, PostEntity post) {
        return PostLikeEntity.builder()
                .post(post)
                .user(user)
                .build();
    }

    public static RefreshTokenEntity basicRefreshTokenEntity(UserEntity user) {
        return RefreshTokenEntity.builder()
                .refreshToken("JWTJWTJWTJWT")
                .user(user)
                .build();
    }

}