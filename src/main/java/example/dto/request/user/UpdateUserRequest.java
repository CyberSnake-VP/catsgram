package example.dto.request.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String password;
    private String email;


    public boolean hasUserName(){
        return username != null && !username.isBlank();
    }

    public boolean hasPassword() {
        return password != null && !password.isBlank();
    }

    public boolean hasEmail() {
        return email != null && !email.isBlank();
    }
}
