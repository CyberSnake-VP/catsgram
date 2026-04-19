package example.controller;

import example.dto.request.post.CreatePostRequest;
import example.dto.request.post.UpdatePostRequest;
import example.dto.response.post.PostResponse;
import example.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody CreatePostRequest request) {
        return postService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse get(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAll() {
        return postService.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse update(@PathVariable Long id,
                               @RequestBody UpdatePostRequest request) {
        return postService.update(id, request);
    }
}
