package olskercupcakes.api;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingFoundException;
import olskercupcakes.domain.cupcake.CupcakeRepository;
import olskercupcakes.domain.order.*;
import olskercupcakes.domain.user.*;

import java.util.List;
import java.util.UUID;

public class OlskerCupcakes {

    private final UserRepository userRepository;
    private final CupcakeRepository cupcakeRepository;
    private final OrderRepository orderRepository;

    public OlskerCupcakes(UserRepository userDBDAO, CupcakeRepository cupcakeRepository, OrderRepository orderRepository) {

        this.userRepository = userDBDAO;
        this.cupcakeRepository = cupcakeRepository;
        this.orderRepository = orderRepository;
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

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
  
    public User updateUser(String email) throws UserNotFoundException {
        return userRepository.findUser(email);
    }

    public OrderFactory createOrder() throws OrderExistsException, UserNotFoundException {
        return orderRepository.createOrder();
    }
    public Order findOrder(UUID uuid) throws OrderNotFoundException, UserNotFoundException {
        return orderRepository.findOrder(uuid);
    }
}
