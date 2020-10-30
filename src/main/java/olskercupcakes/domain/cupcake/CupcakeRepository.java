package olskercupcakes.domain.cupcake;

import java.util.List;

public interface CupcakeRepository {

    List<Cupcake.Topping> findAllToppings() throws CupcakeNoToppingsFoundException;

    List<Cupcake.Cake> findAllCakes() throws CupcakeNoCakeFoundException;

    Cupcake.Cake findCake(int id);

    Cupcake.Topping findTopping(int id);

}
