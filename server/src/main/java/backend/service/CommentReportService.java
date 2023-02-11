package backend.service;

import backend.controller.dto.CommentDto;
import backend.controller.dto.UserDto;
import backend.model.report.CommentReportEntity;
import backend.repository.report.CommentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentReportService {
    private final CommentReportRepository commentReportRepository;

    @Transactional
    public void postCommentReport(UserDto userDto, CommentDto commentDto, String content) {
        CommentReportEntity commentReportEntity = CommentReportEntity.builder()
                .postId(commentDto.getPost().toEntity())
                .commentId(commentDto.toEntity())
                .reporterId(userDto.toEntity())
                .reportedUserId(commentDto.getUser().toEntity())
                .content(content)
                .build();

        commentReportRepository.save(commentReportEntity);
    }
}
