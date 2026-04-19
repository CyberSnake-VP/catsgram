package example.dal.mappers;

import example.model.Post;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setAuthorId(rs.getLong("author_id"));
        post.setDescription(rs.getString("description"));
        post.setPostDate(rs.getDate("post_date").toInstant());
        return post;
    }
}
