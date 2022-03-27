package backend.common;

import backend.model.category.CategoryEntity;
import backend.model.post.CommentEntity;
import backend.model.post.PostEntity;
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


    public static PostEntity basicPostEntity(){
        return PostEntity.builder()
                .postLikeCount(0)
                .postTitle("게시물 제목")
                .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
                .user(authorUserEntity())
                .build();
    }

    public static CategoryEntity basicCategoryEntity(){
        return CategoryEntity.builder()
                .categoryName("음악")
                .build();
    }

    public static CommentEntity basicCommentEntity(){
        return CommentEntity.builder()
                .post(basicPostEntity())
                .user(basicUserEntity())
                .post(basicPostEntity())
                .commentText("민트초코가 짱이라 치약에 민트맛을 넣은거에요! 매일 맛봐도 안질리니까")
                .commentLikeCount(100L)
                .build();
    }

    public static CommentEntity basicCommentEntity2(){
        return CommentEntity.builder()
                .post(basicPostEntity())
                .user(authorUserEntity())
                .post(basicPostEntity())
                .commentText("아 민트는 좀...")
                .commentLikeCount(50L)
                .build();
    }

    public static CommentEntity basicCommentEntity3(){
        return CommentEntity.builder()
                .post(basicPostEntity())
                .user(basicUserEntity())
                .post(basicPostEntity())
                .commentText("민트초코 맛을 모르다니,,,")
                .commentLikeCount(100L)
                .build();
    }

    public static VoteSelectEntity basicVoteSelectEntity(){
        return VoteSelectEntity.builder()
                .user(basicUserEntity())
                .post(basicPostEntity())
                .voteResult(1)  // 1번 Topic 2번 Topic 이라고 가정!
                .build();
    }

}