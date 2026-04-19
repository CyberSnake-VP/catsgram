package example.controller;

import example.dto.request.user.CreateUserRequest;
import example.dto.response.user.UserResponse;
import example.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create( @Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }
}
