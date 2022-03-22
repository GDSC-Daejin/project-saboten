package backend.model;

import lombok.*;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity userId;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "post_like_count", nullable = false)
    private Integer postLikeCount;

    @Builder.Default
    @Column(name = "post_regist_date", nullable = false)
    private LocalDateTime postRegistDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "post_modify_date")
    private LocalDateTime postModifyDate = LocalDateTime.now();
}