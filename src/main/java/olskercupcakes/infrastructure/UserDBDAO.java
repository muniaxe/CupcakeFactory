package olskercupcakes.infrastructure;

import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserExistsException;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.domain.user.UserRepository;

import java.sql.*;

public class UserDBDAO implements UserRepository {

    private final Database db;

    public UserDBDAO(Database db) {
        this.db = db;
    }

    @Override
    public User createUser(String name, byte[] salt, byte[] secret) throws UserExistsException {
        int id;
        try (Connection conn = db.getConnection()) {
            var ps =
                    conn.prepareStatement(
                            "INSERT INTO users (username, salt, secret) " +
                                    "VALUE (?,?,?);",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setBytes(2, salt);
            ps.setBytes(3, secret);
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new UserExistsException();
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {

                id = rs.getInt(1);
            } else {
                throw new UserExistsException(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findUser(id);
    }

    @Override
    public User authorizeUser(String email, String password) {
        return null;
    }

    @Override
    public User findUser(int id) throws UserNotFoundException {
        return null;
    }
}

