package example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class Image {
    private Long id;
    private Long postId;
    private String originalName;
    private String filePath;
}
