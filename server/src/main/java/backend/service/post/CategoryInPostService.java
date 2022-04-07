package backend.service.post;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.repository.post.CategoryInPostRepository;
import common.model.reseponse.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInPostService {
    private final CategoryInPostRepository categoryInPostRepository;

    @Transactional
    public List<Category> findCagegoriesInPost(PostEntity postEntity) {
        List<CategoryInPostEntity> categoryEntities = categoryInPostRepository.findByPost(postEntity);
        List<Category> categories = new ArrayList<>();
        for(CategoryInPostEntity categoryInPostEntity : categoryEntities) {
            categories.add(categoryInPostEntity.getCategory().toDTO());
        }
        return categories;
    }

    @Transactional
    public List<Category> saveCagegoriesInPost(List<CategoryEntity> categoryEntities, PostEntity postEntity){
        List<Category> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntities){
            categoryInPostRepository.save(CategoryInPostEntity.builder().post(postEntity).category(categoryEntity).build());
            categories.add(categoryEntity.toDTO());
        }
        return categories;
    }
}
