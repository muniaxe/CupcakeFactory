package olskercupcakes.web.pages.admin;

import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.order.OrderNotFoundException;
import olskercupcakes.domain.user.User;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderPageServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = super.getUser(req);
            if (user == null || !user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
            List<Order> orders = api.getAllOrders();
            orders.sort(Comparator.comparing(Order::getCreatedAt).reversed());
            req.setAttribute("orders", orders);
            super.render("Admin panel - All Orders", "admin/orders", req, resp);
        } catch (OrderNotFoundException e) {
            e.printStackTrace();
        }
    }
}
