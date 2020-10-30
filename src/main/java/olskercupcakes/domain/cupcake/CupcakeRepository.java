package olskercupcakes.domain.cupcake;

import java.util.List;

public interface CupcakeRepository {

    List<Cupcake.Cake> findAllCakes() throws CupcakeNoCakeFoundException;

    List<Cupcake.Topping> findAllToppings() throws CupcakeNoToppingFoundException;

    Cupcake.Cake findCake(int id) throws CupcakeNoCakeFoundException;

    Cupcake.Topping findTopping(int id) throws CupcakeNoToppingFoundException;

}
