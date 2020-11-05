package olskercupcakes.web.pages;

import olskercupcakes.domain.order.Cart;
import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.order.OrderExistsException;
import olskercupcakes.domain.order.OrderNotFoundException;
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

@WebServlet({"/order", "/order/*"})
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
            Cart tmpCart = (Cart) req.getSession().getAttribute("cart");
            tmpCart.clearCart();
            req.getSession().setAttribute("cart", tmpCart);
            resp.sendRedirect(req.getContextPath() + "/order/" + order.getUuid());

        } catch (OrderExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null) {
            try {
                UUID uuid = UUID.fromString(req.getPathInfo().substring(1));
                Order order = api.findOrder(uuid);
                req.getSession().setAttribute("order", order);

                super.render("Order - " + uuid, "order", req, resp);
            } catch (UserNotFoundException | OrderNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}