package example.services;

import example.dal.PostRepository;
import example.dto.request.post.CreatePostRequest;
import example.dto.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponse create(CreatePostRequest request) {

    }


}
