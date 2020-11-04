package olskercupcakes.web.pages;

import olskercupcakes.domain.order.Cart;
import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.user.User;
import olskercupcakes.web.BaseServlet;
import olskercupcakes.web.Notification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/order")
public class OrderServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        UUID uuid = UUID.randomUUID();
        User user = getUser(req);
        List<Cart.Item> items = cart.getItems();

        Order order = new Order(uuid, user, items);

        api.createOrder(order);

    }
}
