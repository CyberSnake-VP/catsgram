package example.dal;

import example.model.Image;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImageRepository extends BaseRepository<Image> {
    public ImageRepository(JdbcTemplate jdbc, RowMapper<Image> rowMapper) {
        super(jdbc, rowMapper);
    }

    private static final String SAVE_QUERY = "INSERT INTO images (original_name, post_id, file_path) " +
            "VALUES (?, ?, ?) RETURNING id";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM images WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM images WHERE post_id = ?";


    public Image save(Image image) {
        long id = insert(
                SAVE_QUERY,
                image.getOriginalName(),
                image.getPostId(),
                image.getFilePath()
        );
        image.setId(id);
        return image;
    }

    public List<Image> findAll(Long postId) {
        return findAll(FIND_ALL_QUERY, postId);
    }

    public Optional<Image> findOne(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }
}
