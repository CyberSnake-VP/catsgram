package example.services;

import example.dal.UserRepository;
import example.dto.request.user.CreateUserRequest;
import example.dto.request.user.UpdateUserRequest;
import example.dto.response.user.UserResponse;
import example.exception.ConditionNotMetException;
import example.exception.NotFoundException;
import example.mapper.UserMapper;
import example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


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
    private static final String NOT_FOUND_MESSAGE = "Пользователь не найден";

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

    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserResponse)
                .orElseThrow(()-> new NotFoundException(NOT_FOUND_MESSAGE + "c id: " + id));
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    public UserResponse update(Long id, UpdateUserRequest request) {
        // находим по id нашего пользователя, обновляем ему поля, если пользователя нет выбрасываем исключение
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(NOT_FOUND_MESSAGE + " с id:" + id));

        String username = user.getUsername();
        String email = user.getEmail();

        // проверяем данные для обновления на существование, только если username изменился
        if(request.hasUserName()) {
            if(!request.getUsername().equals(username)) {
                boolean isUsername = userRepository.isExistUsername(request.getUsername());
                if(isUsername) {
                    throw new ConditionNotMetException(NAME_EXIST_MESSAGE);
                }
            }
        }

        // проверяем поле на существование только если mail изменился
         if(request.hasEmail()) {
             if(!request.getEmail().equals(email)) {
                 boolean isEmail = userRepository.isExistEmail(request.getEmail());
                 if(isEmail) {
                     throw new ConditionNotMetException(EMAIL_EXIST_MESSAGE);
                 }
             }
         }
         User updatedUser = UserMapper.toUserUpdate(request, user);
         return UserMapper.toUserResponse(userRepository.update(updatedUser));
    }


}
