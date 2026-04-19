package example.dto.request.post;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String description;

    public boolean hadDescription() {
        return description != null && !description.isBlank();
    }
}
