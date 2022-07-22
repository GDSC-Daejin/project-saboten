package backend.service.post;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.repository.post.CategoryInPostRepository;
import common.model.reseponse.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInPostService {
    private final CategoryInPostRepository categoryInPostRepository;

    @Transactional
    public List<CategoryResponse> findCagegoriesInPost(PostEntity postEntity) {
        List<CategoryInPostEntity> categoryEntities = categoryInPostRepository.findByPost(postEntity);
        List<CategoryResponse> categories = new ArrayList<>();
        for(CategoryInPostEntity categoryInPostEntity : categoryEntities) {
            categories.add(categoryInPostEntity.getCategory().toDTO());
        }
        return categories;
    }

    @Transactional
    public List<CategoryResponse> saveCagegoriesInPost(List<CategoryEntity> categoryEntities, PostEntity postEntity){
        List<CategoryResponse> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntities){
            CategoryInPostEntity categoryInPostEntity = CategoryInPostEntity.builder().post(postEntity).category(categoryEntity).build();
            categoryInPostRepository.save(categoryInPostEntity);
            categories.add(categoryEntity.toDTO());
        }
        return categories;
    }

    @Transactional
    public Page<CategoryInPostEntity> findCategoryInPostPageByCategoryId(CategoryEntity categoryEntity, Pageable pageable) {
        return categoryInPostRepository.findALLByCategory(categoryEntity, pageable);
    }

    @Transactional
    public Page<CategoryInPostEntity> findAllCagegoryInPostPage(Pageable pageable) {
        return categoryInPostRepository.findAll(pageable);
    }

    @Transactional
    public List<CategoryResponse> update(PostEntity post, List<CategoryEntity> categoryEntities) {
        List<CategoryInPostEntity> beforeCategiryInPostEntities = categoryInPostRepository.findByPost(post);
        // TODO : 얘 왜 내용이 null 값임??
        CategoryEntity wtf = beforeCategiryInPostEntities.get(0).getCategory();
        deleteAllCategoryInPost(post);
        return saveCagegoriesInPost(categoryEntities, post);
    }

    private void deleteAllCategoryInPost(PostEntity post) {
        categoryInPostRepository.deleteAllByPost(post);
    }
}
