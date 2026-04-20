package example.dto.response.image;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponse {
    private Long id;
    private Long postId;
    private String originalName;
    private String filePath;
}
