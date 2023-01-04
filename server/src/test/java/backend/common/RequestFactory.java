package backend.common;

import common.model.VoteColorsResponse;
import common.model.request.comment.CommentCreateRequest;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.create.VoteCreateRequest;
import common.model.request.post.update.PostUpdateRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestFactory {

    public static PostCreateRequest basicPostCreateRequest(Long... categoryIds) {
        List<VoteCreateRequest> voteCreateRequests = new ArrayList<>();
        voteCreateRequests.add(new VoteCreateRequest("강아지", VoteColorsResponse.BLUE));
        voteCreateRequests.add(new VoteCreateRequest("고양이", VoteColorsResponse.PINK));

        return new PostCreateRequest("어떤 애완동물이 더 귀여울까?!", voteCreateRequests, Arrays.stream(categoryIds).toList());
    }

    public static PostUpdateRequest basicPostUpdateRequest(Long postId, Long... categoryIds) {
        List<VoteCreateRequest> voteCreateRequests = new ArrayList<>();
        voteCreateRequests.add(new VoteCreateRequest("탕수육", VoteColorsResponse.BLUE));
        voteCreateRequests.add(new VoteCreateRequest("치킨", VoteColorsResponse.PINK));

        return new PostUpdateRequest(postId, "좋아하는 음식은?", voteCreateRequests, Arrays.stream(categoryIds).toList());
    }

    public static CommentCreateRequest basicCommentCreateRequest() {
        return new CommentCreateRequest("안녕 테스트 댓글이에요!");
    }
}
