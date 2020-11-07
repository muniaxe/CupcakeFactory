package olskercupcakes.web.pages.admin;

import olskercupcakes.domain.order.Order;
import olskercupcakes.domain.order.OrderNotFoundException;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.web.BaseServlet;
import olskercupcakes.web.Notification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = super.getUser(req);
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        try {
            String action = req.getParameter("action");
            UUID uuid = UUID.fromString(req.getParameter("uuid"));
            if(action == null)
                throw new IllegalArgumentException();

            //Probably change this to a Switch Case...
            Order order = api.findOrder(uuid);
            if(action.equals("delete-order")) {
                order.getStatus().setId(2);
                Order updatedOrder = api.updateOrder(order);

                resp.sendRedirect(req.getContextPath() + "/admin/orders");
                req.getSession().setAttribute("notification", new Notification(Notification.Type.WARNING,
                        "Ordren med id #" + updatedOrder.getUuid() + " er blevet fjernet."
                ));
            }
            else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException | OrderNotFoundException | UserNotFoundException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Der skete en fejl da vi prøvede at fjerne ordren."
            ));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
