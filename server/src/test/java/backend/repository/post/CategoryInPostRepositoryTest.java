package backend.repository.post;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.category.CategoryRepository;
import backend.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryInPostRepositoryTest {

    @Autowired
    private CategoryInPostRepository categoryInPostRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // given
    private UserEntity author = UserEntity.builder()
            .nickname("작성자")
            .build();
    private PostEntity post = PostEntity.builder()
            .postLikeCount(0)
            .postTitle("게시물 제목")
            .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
            .userId(author)
            .build();

    // 테스트 일때만 Create 해주고 실제는 카테고리 Create 기능 제공 안할거에요!
    private CategoryEntity category = CategoryEntity.builder()
            .categoryName("음악")
            .build();

    private CategoryInPostEntity savedCategoryInPost = null;

    @BeforeEach
    private void saveCategoryInPost() {
        userRepository.save(author);
        postRepository.save(post);
        categoryRepository.save(category);

        CategoryInPostEntity categoryInPost = CategoryInPostEntity.builder()
                .post(post)
                .category(category)
                .build();

        savedCategoryInPost = categoryInPostRepository.save(categoryInPost);
    }

    @Nested
    @DisplayName("생성")
    class Create {
        @Test
        public void 게시물_등록시_카테고리_추가() {
            //then
            assertThat(savedCategoryInPost)
                    .isNotNull();
            assertEquals(savedCategoryInPost.getCategory().getCategoryName(), category.getCategoryName());
            assertEquals(savedCategoryInPost.getPost().getPostId(), post.getPostId());
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 게시물_카테고리_조회() {
            // when
            List<CategoryInPostEntity> categories = categoryInPostRepository.findByPost(post);
            //then
            assertThat(categories)
                    .isNotEmpty();
            assertEquals(categories.get(0).getPost().getPostId(), post.getPostId());
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        @Test
        public void 삭제_성공() {
            // given

            // when
            categoryInPostRepository.delete(savedCategoryInPost);

            List<CategoryInPostEntity> findCategoryInPost = categoryInPostRepository.findByPost(post);
            //then
            assertThat(findCategoryInPost)
                    .isEmpty();
        }
    }
}