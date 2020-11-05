package olskercupcakes.web.pages.admin;

import olskercupcakes.domain.order.OrderNotFoundException;
import olskercupcakes.domain.user.User;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/users")
public class AdminUserPageServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", api.getAllUsers());
        User user = super.getUser(req);
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        super.render("Admin panel - All users", "admin/users", req, resp);
    }
}
