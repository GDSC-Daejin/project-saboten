package backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "post_image")
    private String postImage;  //TODO: 게시글에 이미지 여러개 올 수 있지 않을까? 배열로 둬야 할 수도?

    @Column(name = "post_like_count", nullable = false)
    private Integer postLikeCount;

    @Builder.Default
    @Column(name = "post_regist_date", nullable = false)
    private LocalDateTime postRegistDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "post_modify_date")
    private LocalDateTime postModifyDate = LocalDateTime.now();



}