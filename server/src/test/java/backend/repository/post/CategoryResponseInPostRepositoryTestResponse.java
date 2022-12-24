//package backend.repository.post;
//
//import backend.common.EntityFactory;
//import backend.model.category.CategoryEntity;
//import backend.model.post.CategoryInPostEntity;
//import backend.model.post.PostEntity;
//import backend.model.user.UserEntity;
//import backend.repository.category.CategoryRepository;
//import backend.repository.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class CategoryResponseInPostRepositoryTestResponse {
//
//    @Autowired
//    private CategoryInPostRepository categoryInPostRepository;
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    private CategoryInPostEntity categoryInPost = EntityFactory.basicCategoryInPostEntity();
//    private PostEntity post = categoryInPost.getPost();
//    private UserEntity author = post.getUser();
//    private CategoryEntity category = categoryInPost.getCategory();
//
//    @BeforeEach
//    private void setUp() {
//        userRepository.save(author);
//        postRepository.save(post);
//        categoryRepository.save(category);
//        categoryInPostRepository.save(categoryInPost);
//    }
//
//    @Nested
//    @DisplayName("생성")
//    class Create {
//        @Test
//        public void 게시물_등록시_카테고리_추가() {
//            //then
//            assertThat(categoryInPost)
//                    .isNotNull();
//            assertEquals(categoryInPost.getCategory().getCategoryName(), category.getCategoryName());
//            assertEquals(categoryInPost.getPost().getPostId(), post.getPostId());
//        }
//    }
//
//    @Nested
//    @DisplayName("조회")
//    class Read {
//        @Test
//        public void 게시물_카테고리_조회() {
//            // when
//            List<CategoryInPostEntity> categories = categoryInPostRepository.findByPost(post);
//            //then
//            assertThat(categories)
//                    .isNotEmpty();
//            assertEquals(categories.get(0).getPost().getPostId(), post.getPostId());
//        }
//    }
//
//    @Nested
//    @DisplayName("삭제")
//    class Delete {
//        @Test
//        public void 삭제_성공() {
//            // when
//            categoryInPostRepository.delete(categoryInPost);
//
//            List<CategoryInPostEntity> findCategoryInPost = categoryInPostRepository.findByPost(post);
//            //then
//            assertThat(findCategoryInPost)
//                    .isEmpty();
//        }
//    }
//}