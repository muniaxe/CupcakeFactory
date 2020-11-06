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
import java.util.Comparator;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserPageServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = super.getUser(req);
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        List<User> users = api.getAllUsers();
        users.sort(Comparator.comparing(User::getId));
        req.setAttribute("users", users);
        super.render("Admin panel - All users", "admin/users", req, resp);
    }
}
