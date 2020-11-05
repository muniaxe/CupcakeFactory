package olskercupcakes.domain.order;

import olskercupcakes.domain.user.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    OrderFactory createOrder() throws OrderExistsException, UserNotFoundException;
    Order findOrder(UUID uuid) throws OrderNotFoundException, UserNotFoundException;
}
