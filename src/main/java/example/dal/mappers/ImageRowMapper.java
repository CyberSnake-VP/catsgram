package example.dal.mappers;

import example.model.Image;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ImageRowMapper implements RowMapper<Image> {

    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
        Image image = new Image();
        image.setId(rs.getLong("id"));
        image.setOriginalName(rs.getString("original_name"));
        image.setPostId(rs.getLong("post_id"));
        image.setFilePath(rs.getString("file_path"));
        return image;
    }
}
