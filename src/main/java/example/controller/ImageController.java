package example.controller;

import example.dto.response.image.ImageResponse;
import example.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/posts/{postId}/images")
    @ResponseStatus(HttpStatus.OK)
    public List<ImageResponse> getPostImages(@PathVariable Long postId) {
        return imageService.getPostImages(postId);
    }



}
