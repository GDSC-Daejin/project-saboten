package backend.service;

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
    public UserEntity findUserEntity(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isEmpty())
            throw new ApiException(UserResponseMessage.USER_NOT_FOUND);
        else
            return userEntity.get();
    }
}
