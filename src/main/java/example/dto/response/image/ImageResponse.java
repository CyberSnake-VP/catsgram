package example.dto.response.image;

import lombok.Data;

@Data
public class ImageResponse {
    private Long id;
    private Long postId;
    private String originalName;
    private String filePath;
}
