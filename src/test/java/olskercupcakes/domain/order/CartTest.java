package olskercupcakes.domain.order;

import olskercupcakes.api.OlskerCupcakes;
import olskercupcakes.domain.cupcake.Cupcake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    Cart cart = new Cart();

    @Test
    void addItemDuplicateUpdateQuantity() {
        Cupcake cupcake1 = new Cupcake(new Cupcake.Cake(1, "Vanilla", 200), new Cupcake.Topping(2, "Chocolate", 200));
        Cart.Item item = new Cart.Item(cupcake1, 2);
        cart.addItem(item);
        assertEquals(2, cart.findItem(item).getQuantity());

        cart.addItem(item);
        assertEquals(4, cart.findItem(item).getQuantity());
    }
}