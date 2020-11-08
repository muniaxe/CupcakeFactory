package olskercupcakes.domain.order;

import olskercupcakes.domain.order.cart.Cart;
import olskercupcakes.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final UUID uuid;
    private final User user;
    private final List<Cart.Item> items;
    private final LocalDateTime createdAt;
    private final Status status;

    public Order(UUID uuid, User user, List<Cart.Item> items, LocalDateTime createdAt, Status status) {
        this.user = user;
        this.uuid = uuid;
        this.items = items;
        this.createdAt = createdAt;
        this.status = status;
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

    public Status getStatus() {
        return status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(uuid, order.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public static class Status {
        private int id;
        private final String name;

        public Status(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
