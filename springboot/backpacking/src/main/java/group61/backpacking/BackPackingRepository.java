package group61.backpacking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class BackPackingRepository {

    private final JdbcTemplate jdbcTemplate;

    public BackPackingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(User user) {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, user.getUserName(), user.getPassword(), user.getEmail());
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM User WHERE email = ?";
        RowMapper<User> rowMapper = new UserRowMapper();
        return jdbcTemplate.queryForObject(query, rowMapper, email);
    }

    public void deleteUser(User user) {
        String query = "DELETE FROM User (username, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, user.getUserName(), user.getPassword(), user.getEmail());
    }
}
