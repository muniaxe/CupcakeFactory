package olskercupcakes.domain.user;

public interface UserRepository {

    User createUser (String email, byte[] salt, byte[] secret) throws UserExistsException;

    User authorizeUser (String email, String password) throws UserInvalidPasswordException, UserNotFoundException;

}
