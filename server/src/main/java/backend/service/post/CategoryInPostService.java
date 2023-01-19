package backend.service.post;

import backend.controller.dto.CategoryDto;
import backend.controller.dto.CategoryInPostDto;
import backend.controller.dto.PostDto;
import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.repository.post.CategoryInPostRepository;
import backend.util.DurationUtil;
import common.model.request.Duration;
import common.model.reseponse.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryInPostService {
    private final CategoryInPostRepository categoryInPostRepository;

    public List<CategoryResponse> findCategoriesInPost(Long postId) {
        List<CategoryInPostEntity> categoryEntities = categoryInPostRepository.findByPostId(postId);
        List<CategoryResponse> categories = new ArrayList<>();
        for(CategoryInPostEntity categoryInPostEntity : categoryEntities) {
            categories.add(categoryInPostEntity.getCategory().toDto().toCategoryResponse());
        }
        return categories;
    }

    @Transactional
    public List<CategoryResponse> saveCategoriesInPost(final List<CategoryDto> categoryEntities, final PostDto postDto){
        List<CategoryResponse> categories = new ArrayList<>();
        for(CategoryDto categoryDto : categoryEntities){
            CategoryInPostEntity categoryInPostEntity = CategoryInPostEntity.builder()
                    .post(postDto.toEntity())
                    .category(categoryDto.toEntity())
                    .build();
            categoryInPostRepository.save(categoryInPostEntity);
            categories.add(categoryDto.toCategoryResponse());
        }
        return categories;
    }

    public Page<CategoryInPostDto> findCategoryInPostPageByCategoryId(final Long categoryId, final Pageable pageable) {
        return categoryInPostRepository.findALLByCategoryId(categoryId, pageable).map(CategoryInPostEntity::toDto);
    }

    public Page<CategoryInPostDto> findHotCategoryInPost(final Long categoryId, final Duration duration, final Pageable pageable) {
        Page<CategoryInPostDto> categoryInPostPage = findCategoryInPostPageByCategoryId(categoryId, pageable);

        Page<CategoryInPostDto> hotCategoryInPostPage = new PageImpl<>(categoryInPostPage.getContent().stream().filter(categoryInPostDto -> {
            PostDto postDto = categoryInPostDto.getPost();
            return DurationUtil.isIncludeDuration(postDto.getRegistDate(), duration);
        }).collect(Collectors.toList()), categoryInPostPage.getPageable(), categoryInPostPage.getTotalElements());

        return hotCategoryInPostPage;
    }

    @Transactional
    public List<CategoryResponse> update(PostDto postDto, List<CategoryDto> categoriesDto) {
        List<CategoryInPostEntity> beforeCategoryInPostEntities = categoryInPostRepository.findByPostId(postDto.getPostId());
        // TODO : 얘 왜 내용이 null 값임??
        CategoryEntity wtf = beforeCategoryInPostEntities.get(0).getCategory();
        deleteAllCategoryInPost(postDto.toEntity());
        return saveCategoriesInPost(categoriesDto, postDto);
    }

    private void deleteAllCategoryInPost(PostEntity post) {
        categoryInPostRepository.deleteAllByPost(post);
    }
}
