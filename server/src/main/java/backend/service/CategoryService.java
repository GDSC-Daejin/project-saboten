package backend.service;

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

    @Transactional
    public List<CategoryResponse> findCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryResponse> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntities) {
            categories.add(categoryEntity.toDTO());
        }

        return categories;
    }


    @Transactional
    public List<CategoryEntity> findCategories(List<Long> categoryIds) {
        List<CategoryEntity> categoryEntities = new ArrayList<>();

        for(Long categoryId : categoryIds) {
            categoryEntities.add(findCategory(categoryId));
        }
        return categoryEntities;
    }

    @Transactional
    public CategoryEntity findCategory(Long id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if(category.isEmpty())
            throw new ApiException(CategoryResponseMessage.CATEGORY_NOT_FOUND);
        else
            return category.get();
    }

    @Transactional
    public List<CategoryEntity> getCategories(List<Long> categoryIds){
        List<CategoryEntity> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);

            if(categoryEntity == null)
                throw new ApiException(CategoryResponseMessage.CATEGORY_NOT_FOUND);

            categories.add(categoryEntity);
        }

        return categories;
    }
}
