package backend.model.post;

import backend.controller.dto.CategoryDto;
import backend.controller.dto.CategoryInPostDto;
import backend.model.category.CategoryEntity;
import backend.model.common.BaseTimeEntity;
import backend.model.compositekey.CategoryInPostPK;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(CategoryInPostPK.class)
@Table(name = "TB_CategoryInPost")
public class CategoryInPostEntity extends BaseTimeEntity {
    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    public CategoryInPostDto toDto() {
        return CategoryInPostDto.builder()
                .post(post.toDto())
                .category(category.toDto())
                .build();
    }
}
