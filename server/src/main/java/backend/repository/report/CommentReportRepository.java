package backend.repository.report;

import backend.model.report.CommentReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReportEntity, Long> {
}
