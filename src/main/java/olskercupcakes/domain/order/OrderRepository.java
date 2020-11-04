package olskercupcakes.domain.order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    Order createOrder(UUID uuid, int userId, List<Cart.Item> items) throws OrderExistsException;
    Order findOrder(UUID uuid) throws OrderNotFoundException;
}
