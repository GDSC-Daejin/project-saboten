package backend.model;

import backend.model.compositekey.PostImagePK;
import lombok.*;

import javax.persistence.*;

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(PostImagePK.class)
@Table(name = "TB_PostImage")
public class PostImageEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="post_id", nullable = false)
    public PostEntity postId;

    @Id
    @Column(name = "post_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;


    @Column(name="post_image", nullable = false)
    private String postImage;
}