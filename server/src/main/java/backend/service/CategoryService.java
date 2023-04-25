package backend.service;

import backend.controller.dto.CategoryDto;
import backend.exception.ApiException;
import backend.model.category.CategoryEntity;
import backend.repository.category.CategoryRepository;
import common.message.CategoryResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.reseponse.category.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    public List<CategoryResponse> findCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryResponse> categories = new ArrayList<>();

        for(CategoryEntity categoryEntity : categoryEntities) {
            categories.add(categoryEntity.toDto().toCategoryResponse());
        }

        return categories;
    }

    public List<CategoryDto> findCategories(List<Long> categoryIds) {
        List<CategoryDto> categories = new ArrayList<>();

        for(Long categoryId : categoryIds) {
            categories.add(findCategory(categoryId));
        }
        return categories;
    }

    public CategoryDto findCategory(Long id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if(category.isEmpty())
            throw new ApiException(CategoryResponseMessage.CATEGORY_NOT_FOUND);
        else
            return category.get().toDto();
    }

    public List<CategoryDto> getCategories(final List<Long> categoryIds){
        List<CategoryDto> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);

            if(categoryEntity == null)
                throw new ApiException(CategoryResponseMessage.CATEGORY_NOT_FOUND);

            categories.add(categoryEntity.toDto());
        }

        // 전체 카테고리는 게시글에 지정 X
//        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(10L);
//        categories.add(categoryEntity.toDto());
        return categories;
    }
}
