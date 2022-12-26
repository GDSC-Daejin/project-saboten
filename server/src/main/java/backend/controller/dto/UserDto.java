package backend.controller.dto;

import backend.model.user.UserEntity;
import backend.oauth.entity.ProviderType;
import common.model.GenderResponse;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.user.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private Long userId;

    private String socialId;

    private ProviderType providerType;

    private String nickname;

    private Integer age;

    private String myPageIntroduction;

    private Integer gender;

    private String email;

    private String userImage;

    // PostDto를 하든 아예 없애든
//    private List<PostEntity> posts = new ArrayList<>();

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .socialId(socialId)
                .providerType(providerType)
                .nickname(nickname)
                .age(age)
                .myPageIntroduction(myPageIntroduction)
                .gender(gender)
                .email(email)
                .userImage(userImage)
                .build();
    }


    public UserInfoResponse toUserInfoDTO(){
        GenderResponse gender = null;
        if(this.gender == 1) gender = GenderResponse.M;
        else if(this.gender == 2) gender = GenderResponse.F;

        return new UserInfoResponse(this.userId, this.nickname, userImage ,this.email, this.myPageIntroduction,
                this.age, gender);
    }

    public UserResponse toUserResponse() {
        return new UserResponse(userId, nickname, userImage);
    }

}
