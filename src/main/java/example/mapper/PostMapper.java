package example.mapper;

import example.dto.request.post.CreatePostRequest;
import example.dto.request.post.UpdatePostRequest;
import example.dto.response.post.PostResponse;
import example.model.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static PostResponse toPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getAuthorId(),
                post.getDescription(),
                post.getPostDate()
        );
    }

    public static Post toPost(CreatePostRequest request) {
        Post post = new Post();
        post.setAuthorId(request.getAuthorId());
        post.setDescription(request.getDescription());
        return post;
    }

    public static Post toPostUpdate(UpdatePostRequest request, Post post) {
        if(request.hasDescription()) {
            post.setDescription(request.getDescription());
        }
        return post;
    }
}
