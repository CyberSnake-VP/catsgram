package example.services;

import example.dal.PostRepository;
import example.dal.UserRepository;
import example.dto.request.post.CreatePostRequest;
import example.dto.request.post.UpdatePostRequest;
import example.dto.response.post.PostResponse;
import example.exception.NotFoundException;
import example.mapper.PostMapper;
import example.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final String AUTHOR_NOT_FOUND = "Автор поста не найден";
    private static final String POST_NOT_FOUND = "Пост не найден";

    public PostResponse create(CreatePostRequest request) {
        if(!userRepository.isExistId(request.getAuthorId())) {
            throw new NotFoundException(AUTHOR_NOT_FOUND + " id: " + request.getAuthorId());
        }
        return PostMapper.toPostResponse(postRepository.create(PostMapper.toPost(request)));
    }

    public PostResponse findById(Long id) {
        return postRepository.findById(id)
                .map(PostMapper::toPostResponse)
                .orElseThrow(()-> new NotFoundException(POST_NOT_FOUND + "id: " + id));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll().stream()
                .map(PostMapper::toPostResponse)
                .toList();
    }

    public PostResponse update(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .map(p -> PostMapper.toPostUpdate(request, p))
                .orElseThrow(()-> new NotFoundException(POST_NOT_FOUND));
        postRepository.update(post);
        return PostMapper.toPostResponse(post);
    }
}
