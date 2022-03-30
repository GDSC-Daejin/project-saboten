package backend.service;

import backend.exception.ApiException;
import backend.model.category.CategoryEntity;
import backend.repository.category.CategoryRepository;
import common.message.BasicResponseMessage;
import common.model.reseponse.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    public List<Category> findCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<Category> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntities) {
            categories.add(categoryEntity.toDTO());
        }

        return categories;
    }

    public Category findCategory(Long id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if(category.isEmpty())
            throw new ApiException(BasicResponseMessage.NOT_FOUND);     // Category ResponseMessage 정의해야함
        else
            return category.get().toDTO();
    }
}
