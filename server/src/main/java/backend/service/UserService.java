package backend.service;

import backend.controller.dto.UserDto;
import backend.exception.ApiException;
import backend.model.user.UserEntity;
import backend.repository.user.UserRepository;
import common.message.UserResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto findUserEntity(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isEmpty())
            throw new ApiException(UserResponseMessage.USER_NOT_FOUND);
        else
            return userEntity.get().toDto();
    }

    // 소셜로그인 임시용 함수입니다
    @Transactional
    public UserDto findUserEntityByNickname(String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname);

        if(userEntity == null)
            throw new ApiException(UserResponseMessage.USER_NOT_FOUND);
        else
            return userEntity.toDto();
    }
}
