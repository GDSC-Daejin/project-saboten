package backend.repository;

import backend.model.category.CategoryCountEntity;
import backend.repository.category.CategoryRepository;
import backend.repository.post.CategoryInPostRepository;
import backend.repository.post.PostRepository;
import backend.repository.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@DataJpaTest
//@TestPropertySource(properties = {"spring.config.location=classpath:/application-dev.properties"})
@SpringBootTest(properties = {"spring.profiles.active=dev"})
public class CategoryRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryInPostRepository categoryInPostRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void 카테고리_전체조회_테스트() {
        //given
//        List<CategoryEntity> categoryEntityList = new ArrayList<>();
//        for (int i = 0 ; i < 10; i++){
//           categoryEntityList.add(new CategoryEntity(String.valueOf('a' + 1),"1","1","1"));
//        }
//        categoryRepository.saveAll(categoryEntityList);
//        //
//        UserEntity user = UserEntity.builder()
//                .userImage("")
//                .gender(1)
//                .age(0)
//                .email("test@gmail.com")
//                .userImage("")
//                .nickname("")
//                .myPageIntroduction("")
//                .providerType(ProviderType.GOOGLE)
//                .build();
//        userRepository.saveAndFlush(user);
//        List<PostEntity> postEntities = new ArrayList<>();
//        for (int i = 0 ; i < 10; i++){
//            PostEntity postEntity = new PostEntity(null,user, "게시물 내용입니다.", 0, 0, 0);
//            postEntities.add(postEntity);
//        }
//        List<CategoryInPostEntity> re = new ArrayList<>();
//        for (int i = 0 ; i < 10; i++){
//            re.add(new CategoryInPostEntity(postEntities.get(i),categoryEntityList.get(i)));
//        }
//        re.add(new CategoryInPostEntity(postEntities.get(1),categoryEntityList.get(3)));
//        re.add(new CategoryInPostEntity(postEntities.get(2),categoryEntityList.get(3)));
//        re.add(new CategoryInPostEntity(postEntities.get(3),categoryEntityList.get(3)));
//        re.add(new CategoryInPostEntity(postEntities.get(4),categoryEntityList.get(2)));
//        re.add(new CategoryInPostEntity(postEntities.get(5),categoryEntityList.get(2)));
//        postRepository.saveAllAndFlush(postEntities);
//        categoryInPostRepository.saveAllAndFlush(re);
        //when
        List<CategoryCountEntity> entities = categoryInPostRepository.countCategoriesGroupByPostId();
        System.out.println("크기 = " + entities.size());
        for(CategoryCountEntity entity : entities){
            System.out.println("어라라" + entity.getCategory() + " number : " + entity.getCount());
        }
        //then

    }
}
