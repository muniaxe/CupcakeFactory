package olskercupcakes.api;

import olskercupcakes.domain.user.*;

public class OlskerCupcakes {

    private final UserRepository userRepository;

    public OlskerCupcakes(UserRepository userDBDAO) {

        this.userRepository = userDBDAO;
    }

    public User authorizeUser (String email, String password) throws UserNonMatchingPasswordException, UserNotFoundException {
        return userRepository.authorizeUser(email, password);
    }

    public User createUser(String email, String password, String passwordVerify) throws UserExistsException, UserPasswordVerifyException {
        if (!password.equals(passwordVerify)){
            throw new UserPasswordVerifyException();
        }
        byte[] salt = User.genereateSalt();
        byte[] secret = User.calculateSecret(salt, password);
        return userRepository.createUser(email, salt, secret);
    }

}
