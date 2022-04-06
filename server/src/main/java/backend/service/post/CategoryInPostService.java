package backend.service.post;

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
}
