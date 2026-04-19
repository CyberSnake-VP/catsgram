package example.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private Long authorId;
    private String description;
    private Instant postDate;
}
