package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.controller.dto.CategoryDto;
import backend.controller.swagger.response.CategoryNotFoundResponse;
import backend.service.CategoryService;
import backend.service.post.CategoryInPostService;
import common.message.CategoryResponseMessage;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.category.CategoryResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Version1RestController
@RequiredArgsConstructor
class CategoryController {
    private final CategoryService categoryService;
    private final CategoryInPostService categoryInPostService;

    @ApiOperation(value = "카테고리 전체 조회 API", notes = "모든 카테고리 불러오기")
    @GetMapping("/category")
    public ApiResponse<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> categories = categoryInPostService.findHotCategories();
        return ApiResponse.withMessage(categories, CategoryResponseMessage.CATEGORY_FIND_ALL);
    }

    @ApiOperation(value = "카테고리 특정 조회 API", notes = "특정 카테고리 불러오기")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = CategoryNotFoundResponse.class)
    })
    @GetMapping("/category/{id}")
    public ApiResponse<CategoryResponse> getCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findCategory(id);

        CategoryResponse categoryResponse = categoryDto.toCategoryResponse();
        return ApiResponse.withMessage(categoryResponse, CategoryResponseMessage.CATEGORY_FIND_ONE);
    }
}
