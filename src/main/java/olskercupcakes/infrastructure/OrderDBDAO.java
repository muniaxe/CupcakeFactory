package olskercupcakes.infrastructure;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingFoundException;
import olskercupcakes.domain.order.*;
import olskercupcakes.domain.order.cart.Cart;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDBDAO implements OrderRepository {
    private final Database db;
    private final UserDBDAO usersDAO;
    private final CupcakeDBDAO cupcakesDAO;

    public OrderDBDAO(Database db, UserDBDAO userDBDAO, CupcakeDBDAO cupcakeDBDAO) {
        this.db = db;
        this.usersDAO = userDBDAO;
        this.cupcakesDAO = cupcakeDBDAO;
    }

    private Order loadOrder(ResultSet rs) throws SQLException, OrderNotFoundException, UserNotFoundException {
        UUID uuid = UUID.fromString(rs.getString("orders.uuid"));

        List<Cart.Item> items = findItems(uuid);

        User user = usersDAO.findUser(rs.getInt("orders.user"));

        LocalDateTime date = rs.getTimestamp("orders._date").toLocalDateTime();

        Order.Status status = new Order.Status(rs.getInt("os.id"), rs.getString("os.name"));

        return new Order(uuid, user, items, date, status);
    }

    private Cart.Item loadItem(ResultSet rs) throws SQLException {
        Cupcake.Cake cake;
        Cupcake.Topping topping;
        try {
            cake = cupcakesDAO.findCake(rs.getInt("order_items.cake_id"));
            topping = cupcakesDAO.findTopping(rs.getInt("order_items.topping_id"));
        } catch (CupcakeNoCakeFoundException | CupcakeNoToppingFoundException e) {
            throw new RuntimeException("Error pulling the cupcake that was associated with an item in an order.");
        }
        //Find topping and cake from ID in result set.

        Cupcake cupcake = new Cupcake(cake, topping);
        int quantity = rs.getInt("order_items.quantity");

        return new Cart.Item(cupcake, quantity);
    }

    @Override
    public OrderFactory createOrder() throws OrderExistsException, UserNotFoundException {
        return new OrderFactory() {
            @Override
            protected Order commit() {
                UUID uuid = getUuid();
                int userId = getUser().getId();
                List<Cart.Item> items = getCart().getItems();

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

                        ps = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE id = ?");
                        ps.setInt(1, getCart().getTotalPrice());
                        ps.setInt(2, userId);
                        ps.executeUpdate();

                        conn.commit();
                        conn.setAutoCommit(true);

                        return findOrder(uuid);
                    } catch (UserNotFoundException e) {
                        //TODO: Why cast exception, just return a "unknown" user.
                        throw new RuntimeException("We gotta catch this");
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
        };
    }

    @Override
    public Order findOrder(UUID uuid) throws OrderNotFoundException, UserNotFoundException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM orders INNER JOIN order_status os on orders.status = os.id WHERE uuid = ?");
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

    public List<Order> getAllOrders() throws OrderNotFoundException {
        List<Order> list = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String query = "SELECT * FROM orders INNER JOIN order_status os on orders.status = os.id WHERE orders.status != ?;";
            PreparedStatement ps = conn.prepareStatement(query);

            //TODO: Should not be hardcoded (If order is deleted)
            ps.setInt(1, 2);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(loadOrder(rs));
            }
        }
        catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> getAllOrdersByUser(int userId) throws OrderNotFoundException, UserNotFoundException {
        List<Order> list = new ArrayList<>();
        try (Connection conn = db.getConnection()){
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM orders INNER JOIN order_status os on orders.status = os.id WHERE user = ? AND orders.status != ?;");
            ps.setInt(1, userId);

            //TODO: Should not be hardcoded (If order is deleted)
            ps.setInt(2, 2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(loadOrder(rs));
            }
            return list;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Very lite version... = Don't change items.
    @Override
    public Order updateOrder(Order order) throws OrderNotFoundException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement ps =
                    conn.prepareStatement(
                            "UPDATE orders SET status = ? " +
                                    "WHERE uuid = ?");
            ps.setInt(1, order.getStatus().getId());
            ps.setString(2, order.getUuid().toString());

            ps.executeUpdate();
            try {
                return findOrder(order.getUuid());
            } catch (OrderNotFoundException e) {
                throw new RuntimeException("Internal error occurred while relaying the newly created order. (Did someone delete the order as it was created?)");
            }
            catch (UserNotFoundException e) {
                throw new RuntimeException("Internal Error occurred... User deleted from system?");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
