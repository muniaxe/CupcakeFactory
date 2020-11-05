package olskercupcakes.web.pages.user;

import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.order.OrderNotFoundException;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.web.BaseServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/my-orders")
public class UserMyOrdersServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = super.getUser(req);
        if (user == null){
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        try {
            req.setAttribute("orders", api.getAllOrdersByUser(user));
        } catch (OrderNotFoundException | UserNotFoundException e) {
            e.printStackTrace();
        }
        super.render("Mine Ordre - Olsker Cupcakes", "user/my-orders", req, resp);
    }
}
