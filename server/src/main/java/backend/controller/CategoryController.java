package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.service.CategoryService;
import common.message.CategoryResponseMessage;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.category.Category;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Version1RestController
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "카테고리 전체 조회 API", notes = "모든 카테고리 불러오기")
    @GetMapping("/category")
    public ApiResponse<List<Category>> getCategories() {
        List<Category> categories = categoryService.findCategories();
        return ApiResponse.withMessage(categories, CategoryResponseMessage.CATEGORY_FIND_ALL);
    }

    @GetMapping("/category/{id}")
    public ApiResponse<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.findCategory(id);
        return ApiResponse.withMessage(category, CategoryResponseMessage.CATEGORY_FIND_ONE);
    }
}
