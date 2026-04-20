package example.controller;

import example.dto.response.image.ImageResponse;
import example.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/posts/{postId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ImageResponse> savePostImages(@PathVariable Long postId,
                                              @RequestParam("image") List<MultipartFile> files) {
        return imageService.saveImages(files, postId);
    }



}
