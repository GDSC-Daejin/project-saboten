package backend.model.category;

import backend.controller.dto.CategoryDto;
import common.model.reseponse.category.CategoryResponse;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Category")
@ToString
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "category_icon_url", nullable = false)
    private String categoryIconUrl;

    @Column(name = "category_start_color")
    private String categoryStartColor;

    @Column(name = "category_end_color")
    private String categoryEndColor;

//    public CategoryResponse toCategoryResponse() {
//        return new CategoryResponse(this.categoryId, this.categoryName, this.categoryIconUrl);
//    }
    public CategoryEntity(String categoryName, String categoryIconUrl, String categoryStartColor,
                          String categoryEndColor) {
        this.categoryName = categoryName;
        this.categoryIconUrl = categoryIconUrl;
        this.categoryStartColor = categoryStartColor;
        this.categoryEndColor = categoryEndColor;
    }

    public CategoryDto toDto() {
        return CategoryDto.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .categoryIconUrl(categoryIconUrl)
                .startColor(categoryStartColor)
                .endColor(categoryEndColor)
                .build();
    }

}