package olskercupcakes.web.pages.admin;

import olskercupcakes.domain.order.OrderNotFoundException;
import olskercupcakes.domain.user.User;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/orders")
public class AdminOrderPageServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("orders", api.getAllOrders());
            User user = super.getUser(req);
            if (user == null || !user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
            super.render("Admin panel - All Orders", "admin/orders", req, resp);
        } catch (
                OrderNotFoundException e) {
            e.printStackTrace();
        }
    }
}
