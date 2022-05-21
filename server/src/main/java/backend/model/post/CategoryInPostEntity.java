package backend.model.post;

import backend.model.category.CategoryEntity;
import backend.model.compositekey.CategoryInPostPK;
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
