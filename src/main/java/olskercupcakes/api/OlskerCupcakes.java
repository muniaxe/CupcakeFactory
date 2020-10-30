package olskercupcakes.api;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingFoundException;
import olskercupcakes.domain.cupcake.CupcakeRepository;
import olskercupcakes.domain.user.*;

import java.util.List;

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

    public List<Cupcake.Topping> findAllCupcakeToppings() throws CupcakeNoToppingFoundException {
        return cupcakeRepository.findAllToppings();
    }

    public List<Cupcake.Cake> findAllCupcakeCakes() throws CupcakeNoCakeFoundException{
        return cupcakeRepository.findAllCakes();
    }

    public Cupcake.Cake findCupcakeCake(int id) throws CupcakeNoCakeFoundException {
        return cupcakeRepository.findCake(id);
    }

    public Cupcake.Topping findCupcakeTopping(int id) throws CupcakeNoToppingFoundException {
        return cupcakeRepository.findTopping(id);
    }
}
