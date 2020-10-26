package olskercupcakes.api;

import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserInvalidPasswordException;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.domain.user.UserRepository;

public class OlskerCupcakes {

    private final UserRepository userRepository;

    public OlskerCupcakes(UserRepository userDBDAO) {

        this.userRepository = userDBDAO;
    }

    public User authorizeUser (String email, String password) throws UserInvalidPasswordException, UserNotFoundException {
        return userRepository.authorizeUser(email, password);
    }

}
