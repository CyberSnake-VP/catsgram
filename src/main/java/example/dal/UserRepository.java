package example.dal;

import example.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class UserRepository extends BaseRepository<User> {
    private static final String INSERT_QUERY = "INSERT INTO users (username, email, password, registration_date) " +
            "VALUES (?, ?, ?, ?) RETURNING id";

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> rowMapper) {
        super(jdbc, rowMapper);
    }

    public User save(User user) {
        long id = insert(
                INSERT_QUERY,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Timestamp.from(user.getRegistrationDate())
        );
        user.setId(id);
        return user;
    }

}
