package example.controller;

import example.dto.response.image.ImageData;
import example.dto.response.image.ImageResponse;
import example.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
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

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageData imageData = imageService.getImageDate(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("video/mp4"));
        headers.setContentDisposition(
                ContentDisposition
                        .inline() // attachment(скачивание файла)
                        .filename(imageData.getName())
                        .build()
        );

        return new ResponseEntity<>(imageData.getData(),headers, HttpStatus.OK);
    }
}
