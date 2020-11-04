package olskercupcakes.web.pages;

import olskercupcakes.domain.order.Cart;
import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.order.OrderExistsException;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@WebServlet("/order")
public class OrderServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isUser(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        try {
            Cart cart = (Cart) req.getSession().getAttribute("cart");

            UUID uuid = UUID.randomUUID();
            User user = getUser(req);
            List<Cart.Item> items = cart.getItems();

            Order order = api.createOrder(uuid, user, items);
            req.getSession().setAttribute("order", order);

            LocalDateTime orderCreatedAt = order.getCreatedAt();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
            String time = orderCreatedAt.format(formatter);
            System.out.println(req.getSession().getAttribute("order"));

            req.getSession().setAttribute("createdAt", time);
            super.render("Order - " + uuid, "order", req, resp);
        } catch (OrderExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}