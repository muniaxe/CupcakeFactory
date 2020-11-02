package olskercupcakes.domain.order;

import olskercupcakes.domain.user.User;

import java.util.List;
import java.util.UUID;

public class Order {
    private final int id;
    private final User user;
    private final UUID uuid;
    private final List<Cart.Item> items;

    public Order(int id, User user, UUID uuid, List<Cart.Item> items) {
        this.id = id;
        this.user = user;
        this.uuid = uuid;
        this.items = items;
    }
}
