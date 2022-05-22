package backend.model.user;

import backend.model.common.BaseTimeEntity;
import backend.model.post.PostEntity;
import backend.oauth.entity.ProviderType;
import common.model.GenderResponse;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.user.UserResponse;
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

    @Column(name = "user_social_id")
    private String socialId;

    @Column(name = "user_provider_type")
    private ProviderType providerType;

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
    public UserResponse toDto() {
        return new UserResponse(this.userId, this.nickname, "url");
    }

    // url, email 수정 필요  //TODO: url, email 테이블에 추가하는 것도 좋은 것 같기도?
    public UserInfoResponse toUserInfoDTO(){
        GenderResponse gender = null;
        if(this.gender == 1) gender = GenderResponse.M;
        else if(this.gender == 2) gender = GenderResponse.F;

        return new UserInfoResponse(this.userId, this.nickname, "url","want@email.com",this.myPageIntroduction,
                this.age, gender);
    }
}