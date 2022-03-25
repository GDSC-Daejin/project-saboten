package backend.model.post;

import backend.model.category.CategoryEntity;
import backend.model.compositekey.CategoryInPostPK;
import backend.model.post.PostEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(CategoryInPostPK.class)
@Table(name = "TB_CategoryInPost")
public class CategoryInPostEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
