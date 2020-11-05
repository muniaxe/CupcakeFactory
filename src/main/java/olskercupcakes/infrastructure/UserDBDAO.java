package olskercupcakes.infrastructure;

import olskercupcakes.domain.user.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBDAO implements UserRepository {

    private final Database db;

    public UserDBDAO(Database db) {
        this.db = db;
    }

    private User loadUser(ResultSet rs) throws SQLException {
        //User(int id, String email, LocalDateTime createdAt, byte[] salt, byte[] secret, int balance)
        return new User(
                rs.getInt("users.id"),
                rs.getString("users.email"),
                rs.getTimestamp("users._date").toLocalDateTime(),
                rs.getBytes("users.salt"),
                rs.getBytes("users.secret"),
                rs.getInt("users.balance"),
                rs.getBoolean("users.is_admin")
        );
    }

    @Override
    public UserFactory createUser() throws UserExistsException {
        return new UserFactory() {
            @Override
            public User commit() {
                int id;

                String email = super.getEmail();
                byte[] salt = User.generateSalt();
                byte[] secret = User.calculateSecret(salt, super.getPassword());

                try (Connection conn = db.getConnection()) {
                    PreparedStatement ps =
                            conn.prepareStatement(
                                    "INSERT INTO users (email, salt, secret) " +
                                            "VALUE (?,?,?);",
                                    Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, email);
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
                        try {
                            return findUser(id);
                        } catch (UserNotFoundException e) {
                            throw new RuntimeException("Internal error occurred while relaying the newly created user. (Did someone delete the user as it was created?)");
                        }
                    } else {
                        throw new UserExistsException();
                    }
                } catch (SQLException | UserExistsException e) {
                    throw new RuntimeException(e);
                }
            }
        };

    }

    @Override
    public User authorizeUser(String email, String password) throws UserNotFoundException, UserNonMatchingPasswordException {
        User user = findUser(email);
        if(user.isPasswordCorrect(password)) {
            return user;
        }
        else {
            throw new UserNonMatchingPasswordException();
        }
    }

    @Override
    public User findUser(int id) throws UserNotFoundException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM users WHERE id = ?;");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return loadUser(rs);
            } else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUser(String email) throws UserNotFoundException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM users WHERE email = ?;");
            s.setString(1, email);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return loadUser(rs);
            } else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String query = "SELECT * FROM users WHERE is_admin = 0;";
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                users.add(loadUser(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

