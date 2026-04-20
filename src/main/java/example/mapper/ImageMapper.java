package example.mapper;

import example.dto.response.image.ImageResponse;
import example.model.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageMapper {

    public static ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getPostId(),
                image.getOriginalName(),
                image.getFilePath()
        );
    }
}
