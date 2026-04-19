package example.mapper;

import example.dto.request.user.CreateUserRequest;
import example.dto.request.user.UpdateUserRequest;
import example.dto.response.user.UserResponse;
import example.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRegistrationDate()
        );
    }

    public static User toUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public static User toUserUpdate(UpdateUserRequest request, User user) {
        if (request.hasUserName()) {
            user.setUsername(request.getUsername());
        }
        if (request.hasEmail()) {
            user.setEmail(request.getEmail());
        }
        if (request.hasPassword()) {
            user.setPassword(request.getPassword());
        }
        return user;
    }

}
