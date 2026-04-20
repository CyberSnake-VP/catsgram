package example.services;

import example.dal.ImageRepository;
import example.dal.PostRepository;
import example.dto.response.image.ImageResponse;
import example.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;


    public List<ImageResponse> getPostImages(Long postId) {
        return imageRepository.findAll(postId).stream()
                .map(ImageMapper::toImageResponse)
                .toList();
    }


}
