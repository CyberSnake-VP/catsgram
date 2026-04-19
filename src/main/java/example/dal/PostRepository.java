package example.dal;

import example.model.Post;
import example.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public class PostRepository extends BaseRepository<Post> {
    public PostRepository(JdbcTemplate jdbc, RowMapper<Post> rowMapper) {
        super(jdbc, rowMapper);
    }

    private static final String INSERT_QUERY = "INSERT INTO posts (author_id, description, post_date) " +
            "VALUES (?, ?, ?) RETURNING id";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM posts WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM posts";
    private static final String UPDATE_QUERY = "UPDATE posts SET description = ? WHERE id = ?";


    public Post create(Post post) {
        long id = insert(
                INSERT_QUERY,
                post.getAuthorId(),
                post.getDescription(),
                Timestamp.from(post.getPostDate())
        );
        post.setId(id);
        return post;
    }

    public Optional<Post> findById(Long id) {
        return findOne(GET_BY_ID_QUERY, id);
    }

    public List<Post> findAll() {
        return findAll(GET_ALL);
    }

    public Post update(Post post) {
        update(
                UPDATE_QUERY,
                post.getDescription(),
                post.getId()
        );
        return post;
    }
}
