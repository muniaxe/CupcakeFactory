package olskercupcakes.domain.user;

import java.util.List;

public interface UserRepository {

    User createUser (String email, byte[] salt, byte[] secret) throws UserExistsException;

    User authorizeUser (String email, String password) throws UserNonMatchingPasswordException, UserNotFoundException;

    User findUser(int id) throws UserNotFoundException;

    User findUser(String email) throws UserNotFoundException;

    List<User> getAllUsers();
}
