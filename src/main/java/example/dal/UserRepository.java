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
    private static final String EMAIL_EXIST_QUERY = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
    private static final String NAME_EXIST_QUERY = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
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

    public boolean isExistEmail(String email) {
        return exist(EMAIL_EXIST_QUERY, email);
    }

    public boolean isExistUsername(String name) {
        return exist(NAME_EXIST_QUERY, name);
    }

}
