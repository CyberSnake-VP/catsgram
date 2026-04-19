package example.services;

import example.dal.UserRepository;
import example.dto.request.user.CreateUserRequest;
import example.dto.response.user.UserResponse;
import example.exception.ConditionNotMetException;
import example.mapper.UserMapper;
import example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class UserService {
    /**
     * 1. Выполняем проверка на заполнение важных полей объекта
     * 2. Проверяем объект на существование в бд через получение по id
     * 3. Mappin в User
     * 3. Записываем в бд
     * 4. Маппим в dto и возвращаем клиенту.
     */
    private final UserRepository userRepository;
    private static final String EMAIL_EXIST_MESSAGE = "Email уже используется";
    private static final String NAME_EXIST_MESSAGE = "Username уже используется";


    public UserResponse create(CreateUserRequest request) {
        boolean isName = userRepository.isExistUsername(request.getUsername());
        boolean isEmail = userRepository.isExistEmail(request.getEmail());
        if (isName) {
            throw new ConditionNotMetException(NAME_EXIST_MESSAGE);
        }
        if (isEmail) {
            throw new ConditionNotMetException(EMAIL_EXIST_MESSAGE);
        }

        User user = UserMapper.toUser(request);
        user.setRegistrationDate(Instant.now());

        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

}
