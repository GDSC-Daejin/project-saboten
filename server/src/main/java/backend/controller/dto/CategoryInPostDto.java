package backend.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryInPostDto {
    private PostDto post;

    private CategoryDto category;
}
