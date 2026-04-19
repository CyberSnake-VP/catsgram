package example.dal;

import example.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {
    public UserRepository(JdbcTemplate jdbc, RowMapper<User> rowMapper) {
        super(jdbc, rowMapper);
    }

    private static final String INSERT_QUERY = "INSERT INTO users (username, email, password, registration_date) " +
            "VALUES (?, ?, ?, ?) RETURNING id";
    private static final String EMAIL_EXIST_QUERY = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
    private static final String NAME_EXIST_QUERY = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_QUERY  = "SELECT * FROM users";
    private static final String UPDATE_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String EXIST_BY_ID = "SELECT 1 FROM users WHERE id = ?";

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

    public Optional<User> findById(Long id) {
        return findOne(GET_BY_ID_QUERY, id);
    }

    public List<User> findAll() {
        return findAll(GET_ALL_QUERY );
    }

    public User update(User user) {
        update(
                UPDATE_QUERY,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getId()
        );
        return user;
    }


    public boolean isExistEmail(String email) {
        return exist(EMAIL_EXIST_QUERY, email);
    }

    public boolean isExistUsername(String name) {
        return exist(NAME_EXIST_QUERY, name);
    }

    public boolean isExistId(Long id) {
        return exist(EXIST_BY_ID, id);
    }


}
