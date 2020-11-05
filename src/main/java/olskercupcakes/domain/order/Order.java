package olskercupcakes.domain.order;

import olskercupcakes.domain.order.cart.Cart;
import olskercupcakes.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID uuid;
    private final User user;
    private final List<Cart.Item> items;
    private final LocalDateTime createdAt;

    public Order(UUID uuid, User user, List<Cart.Item> items, LocalDateTime createdAt) {
        this.user = user;
        this.uuid = uuid;
        this.items = items;
        this.createdAt = createdAt;
    }

    public List<Cart.Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getTotalQuantity() {
        return calculateQuantity();
    }

    private int calculateQuantity() {
        int quantity = 0;
        for(Cart.Item item : items) {
            quantity += item.getQuantity();
        }
        return quantity;
    }

    public int getTotalPrice() {
        return calculatePrice();
    }

    private int calculatePrice() {
        int price = 0;
        for(Cart.Item item : items) {
            price += item.getTotalPrice();
        }
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid=" + uuid +
                ", user=" + user +
                ", items=" + items +
                ", createdAt=" + createdAt +
                '}';
    }
}
