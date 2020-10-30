package olskercupcakes.api;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingsFoundException;
import olskercupcakes.domain.cupcake.CupcakeRepository;
import olskercupcakes.domain.user.*;

import javax.ejb.Local;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OlskerCupcakes {

    private final UserRepository userRepository;
    private final CupcakeRepository cupcakeRepository;

    public OlskerCupcakes(UserRepository userDBDAO, CupcakeRepository cupcakeRepository) {

        this.userRepository = userDBDAO;
        this.cupcakeRepository = cupcakeRepository;
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

    public Cupcake createCupcake(int toppingId, int cakeId){
        return null;
    }

    public List<Cupcake.Topping> findAllCupcakeToppings() throws CupcakeNoToppingsFoundException {
        return cupcakeRepository.findAllToppings();
    }

    public List<Cupcake.Cake> findAllCupcakeCakes() throws CupcakeNoCakeFoundException{
        return cupcakeRepository.findAllCakes();
    }
}
