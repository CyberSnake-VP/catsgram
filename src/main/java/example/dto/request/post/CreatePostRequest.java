package example.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostRequest {
    @NotNull(message = "Author Id is required")
    @Positive(message = "Author Id must be positive number")
    private Long authorId;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 1000, message = "Description must be at lest 1 and max 1000")
    private String description;
}
