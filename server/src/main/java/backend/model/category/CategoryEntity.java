package backend.model.category;

import common.model.reseponse.category.CategoryResponse;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "category_icon_url", nullable = false)
    private String categoryIconUrl;

    public CategoryResponse toDTO() {
        return new CategoryResponse(this.categoryId, this.categoryName, this.categoryIconUrl);
    }
}