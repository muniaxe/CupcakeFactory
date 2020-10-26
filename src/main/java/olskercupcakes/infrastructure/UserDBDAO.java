package olskercupcakes.infrastructure;

import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserExistsException;
import olskercupcakes.domain.user.UserRepository;

public class UserDBDAO implements UserRepository {

    private final Database db;

    public UserDBDAO(Database db) {
        this.db = db;
    }

    @Override
    public User createUser(String email, byte[] salt, byte[] secret) throws UserExistsException {
        return null;
    }

    @Override
    public User authorizeUser(String email, String password) {
        return null;
    }
}

