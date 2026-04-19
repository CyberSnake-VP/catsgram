package example.services;

import example.dal.UserRepository;
import example.dto.request.user.CreateUserRequest;
import example.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    public UserResponse create(CreateUserRequest request) {

    }

}
