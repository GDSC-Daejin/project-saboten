package backend.common;

import backend.model.category.CategoryEntity;
import backend.model.post.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
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
                .postTitle("게시물 제목")
                .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
                .userId(authorUserEntity())
                .build();
    }

    public static CategoryEntity basicCategoryEntity(){
        return CategoryEntity.builder()
                .categoryName("음악")
                .build();
    }

    public static CommentEntity basicCommentEntity(){
        return CommentEntity.builder()
                .commentText("민트초코가 짱이라 치약에 민트맛을 넣은거에요! 매일 맛봐도 안질리니까")
                .commentLikeCount(100L)
                .build();
    }


}