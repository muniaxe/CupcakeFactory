package olskercupcakes.domain.user;

public interface UserRepository {

    UserFactory createUser() throws UserExistsException;

    User authorizeUser (String email, String password) throws UserNonMatchingPasswordException, UserNotFoundException;

    User findUser(int id) throws UserNotFoundException;

    User findUser(String email) throws UserNotFoundException;

}
