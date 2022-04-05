package backend.model.user;

import backend.common.BaseTimeEntity;
import backend.model.post.PostEntity;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_User")
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "user_mypage_introduction")
    private String myPageIntroduction;

    @Column(name = "user_gender")
    private Integer gender;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts = new ArrayList<>();

    // 테스트용
    public UserEntity(UserSignUpRequest userSignInRequest) {
        this.nickname = userSignInRequest.getNickname();
        this.myPageIntroduction = userSignInRequest.getIntroduction();
        this.age = userSignInRequest.getAge();
        this.gender = userSignInRequest.getGender().getValue();
    }

    // url 수정 필요
    public User toDto() {
        return new User(this.userId, this.nickname, "url");
    }
}