package olskercupcakes.infrastructure;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.order.*;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserExistsException;
import olskercupcakes.domain.user.UserNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDBDAO implements OrderRepository {
    private final Database db;

    public OrderDBDAO(Database db) {
        this.db = db;
    }

    private Order loadOrder(ResultSet rs) throws SQLException, OrderNotFoundException {
        //User(int id, String email, LocalDateTime createdAt, byte[] salt, byte[] secret, int balance)
        UUID uuid = UUID.fromString(rs.getString("order.uuid"));

        List<Cart.Item> items = findItems(uuid);

        User user = null;
        //Set user from id

        LocalDateTime date = rs.getTimestamp("order._date").toLocalDateTime();

        return new Order(uuid, user, null, date);
    }

    private Cart.Item loadItem(ResultSet rs) throws SQLException {
        Cupcake.Cake cake = null;
        Cupcake.Topping topping = null;
        //Find topping and cake from ID in result set.

        Cupcake cupcake = new Cupcake(cake, topping);
        int quantity = rs.getInt("order_items.quantity");

        return new Cart.Item(cupcake, quantity);
    }

    @Override
    public Order createOrder(UUID uuid, int userId, List<Cart.Item> items) throws OrderExistsException {
        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            try {
                PreparedStatement ps =
                        conn.prepareStatement(
                                "INSERT INTO orders (uuid, user) " +
                                        "VALUE (?,?);");
                ps.setString(1, uuid.toString());
                ps.setInt(2, userId);

                ps.executeUpdate();

                ps = conn.prepareStatement(
                        "INSERT INTO order_items (order_uuid, cake_id, topping_id, quantity) " +
                                "VALUES (?, ?, ?, ?)");
                for (Cart.Item item : items) {
                    ps.setString(1, uuid.toString());
                    ps.setInt(2, item.getCupcake().getCake().getId());
                    ps.setInt(3, item.getCupcake().getTopping().getId());
                    ps.setInt(4, item.getQuantity());
                    ps.executeUpdate();
                }

                conn.commit();
                conn.setAutoCommit(true);

                return findOrder(uuid);
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(true);
                throw new RuntimeException("An error happened during inserting a new order in the DB system... Good luck.: " + e.getMessage());
            }
            catch (OrderNotFoundException e) {
                throw new RuntimeException("Internal error occurred while relaying the newly created order. (Did someone delete the order as it was created?)");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findOrder(UUID uuid) throws OrderNotFoundException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM orders WHERE uuid = ?;");
            s.setString(1, uuid.toString());
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return loadOrder(rs);
            } else {
                throw new OrderNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cart.Item> findItems(UUID orderUUID) throws OrderNotFoundException {
        List<Cart.Item> items = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM order_items WHERE order_uuid = ?;");
            s.setString(1, orderUUID.toString());
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                items.add(loadItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!items.isEmpty()) {
            return items;
        }
        else {
            throw new OrderNotFoundException();
        }

    }
}