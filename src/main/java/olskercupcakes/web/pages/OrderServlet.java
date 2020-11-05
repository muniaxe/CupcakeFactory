package olskercupcakes.web.pages;

import olskercupcakes.domain.order.Cart;
import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.order.OrderExistsException;
import olskercupcakes.domain.order.OrderFactory;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.domain.validation.ValidationErrorException;
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
        if(!isUser(req)) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        try {
            Cart cart = (Cart) req.getSession().getAttribute("cart");
            UUID uuid = UUID.randomUUID();
            User user = getUser(req);

            OrderFactory orderFactory = api.createOrder();
            orderFactory.setUuid(uuid);
            orderFactory.setUser(user);
            orderFactory.setCart(cart);

            Order order = orderFactory.validateAndCommit();
            resp.getWriter().println(order.toString());
        } catch (OrderExistsException | UserNotFoundException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Der skete en fejl i systemet, prøv igen. Fejlkode: ORDER_EXISTED_WITH_ID"
            ));
            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (ValidationErrorException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Der skete en fejl."
            ));
            req.getSession().setAttribute("notificationProblems", e.getProblems());
            resp.sendRedirect(req.getContextPath() + "/cart");
        }

    }
}
