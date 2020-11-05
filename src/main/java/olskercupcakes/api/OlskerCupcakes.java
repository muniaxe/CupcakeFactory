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

    public UserFactory createUser() throws UserExistsException {
        return userRepository.createUser();
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
