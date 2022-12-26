package backend.controller.dto;

import common.model.reseponse.category.CategoryResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryDto {
    private Long categoryId;

    private String categoryName;

    private String categoryIconUrl;

    public CategoryResponse toCategoryResponse() {
        return new CategoryResponse(this.categoryId, this.categoryName, this.categoryIconUrl);
    }
}
